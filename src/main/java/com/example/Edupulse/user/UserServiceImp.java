package com.example.Edupulse.user;


import com.example.Edupulse.exception.BadRequestException;
import com.example.Edupulse.exception.ResourceNotFoundException;
import com.example.Edupulse.school.School;
import com.example.Edupulse.school.SchoolRepo;
import com.example.Edupulse.security.CookieBuilder;
import com.example.Edupulse.user.dto.CreateUserRequest;
import com.example.Edupulse.user.dto.LoginRequest;
import com.example.Edupulse.utils.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final CookieBuilder cookieBuilder;
    private final SchoolRepo schoolRepo;

    @Autowired
    public UserServiceImp(UserRepository userRepo,
                          PasswordEncoder passwordEncoder,
                          JwtUtils jwtUtils,
                          CookieBuilder cookieBuilder,
                          SchoolRepo schoolRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.cookieBuilder = cookieBuilder;
        this.schoolRepo = schoolRepo;
    }

    @Override
    public String registerUser(CreateUserRequest request) {

        School school = schoolRepo.findById(request.getSchoolId())
                .orElseThrow(() ->
                        new BadRequestException("School not found")
                );

        if (userRepo.existsByEmail(request.getEmail())) {
            throw new BadRequestException(
                    "User already exists with this email"
            );
        }

        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .passwordHash(
                        passwordEncoder.encode(request.getPassword())
                )
                .role(request.getRole())
                .school(school)
                .phone(request.getPhone())
                .profilePic(request.getProfilePic())
                .address(request.getAddress())
                .build();

        userRepo.save(user);

        return "User registered successfully";
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

        String accessToken = jwtUtils.generateAccessToken(user.getId(),
                user.getEmail(), user.getUsername(), user.getRole(), user.getSchool().getId().toString());

        String refreshToken = jwtUtils.generateRefreshToken(user.getId(),
                user.getEmail(), user.getUsername(), user.getRole(), user.getSchool().getId().toString());

        cookieBuilder.setAccessTokenCookie(response, accessToken);
        cookieBuilder.setRefreshTokenCookie(response, refreshToken);

    }

    @Override
    public void logout(HttpServletResponse response) {
        cookieBuilder.clearCookies(response);
    }
}
