package com.example.EduPulse.user;


import com.example.EduPulse.exception.BadRequestException;
import com.example.EduPulse.exception.ResourceNotFoundException;
import com.example.EduPulse.school.School;
import com.example.EduPulse.school.SchoolRepo;
import com.example.EduPulse.security.CookieBuilder;
import com.example.EduPulse.user.dto.request.CreateUserRequest;
import com.example.EduPulse.user.dto.request.UserRequest;
import com.example.EduPulse.user.dto.request.LoginRequest;
import com.example.EduPulse.user.handler.UserHandler;
import com.example.EduPulse.user.handler.UserRoleHandlerFactory;
import com.example.EduPulse.utils.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final CookieBuilder cookieBuilder;
    private final SchoolRepo schoolRepo;
    private final UserResponseFactory userResponseFactory;
    private final UserRoleRepo userRoleRepo;
    private final UserRoleHandlerFactory userRoleHandlerFactory;

    @Override
    @Transactional
    public void registerUser(CreateUserRequest request) {

        UserRole userRole = userRoleRepo.findById(request.getRoleId()).orElseThrow(
                () -> new BadRequestException("Role Not Found")
        );

        School school = null;

        if (!userRole.getName().equals("SUPER_ADMIN") && request.getSchoolId() == null) {
            throw new BadRequestException("SchoolId is required for this role");
        }

        if (userRepo.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        if (request.getSchoolId() != null) {
            school = schoolRepo.findById(request.getSchoolId())
                    .orElseThrow(() -> new ResourceNotFoundException("School not found"));
        }

        User user = User.builder()
                .school(school)
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(userRole)
                .phone(request.getPhone())
                .address(request.getAddress())
                .profilePic(request.getProfilePic())
                .build();

        userRepo.save(user);

        UserHandler handler = userRoleHandlerFactory.getHandler(userRole.getName());
        handler.handle(user, request);


    }

    @Override
    public void updateUser(UUID userId, UserRequest request) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found"));

        if (request.getUsername() != null)   user.setUsername(request.getUsername());
        if (request.getProfilePic() != null) user.setProfilePic(request.getProfilePic());
        if (request.getPhone() != null)      user.setPhone(request.getPhone());
        if (request.getAddress() != null)    user.setAddress(request.getAddress());

        userRepo.save(user);
    }

    @Override
    public void login(LoginRequest loginRequest, HttpServletResponse response) {
        User user = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow(
                () -> new ResourceNotFoundException("User Does Not Exists")
        );

        if (!passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPasswordHash()
        )) {
            throw new BadRequestException("Invalid email or password");
        }

        String schoolId = null;

        if (user.getSchool() != null) {
            schoolId = user.getSchool().getId().toString();
        }


        String accessToken = jwtUtils.generateAccessToken(user.getId(),
                user.getEmail(), user.getUsername(), user.getRole().getName(), schoolId);

        String refreshToken = jwtUtils.generateRefreshToken(user.getId(),
                user.getEmail(), user.getUsername(), user.getRole().getName(), schoolId);

        cookieBuilder.setAccessTokenCookie(response, accessToken);
        cookieBuilder.setRefreshTokenCookie(response, refreshToken);

    }

    @Override
    public void logout(HttpServletResponse response) {
        cookieBuilder.clearCookies(response);
    }

    @Override
    public Object getProfile(String userId, String role) {
        UUID id = UUID.fromString(userId);

        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserHandler handler = userRoleHandlerFactory.getHandler(user.getRole().getName());

        return handler.buildResponse(user);
    }
}
