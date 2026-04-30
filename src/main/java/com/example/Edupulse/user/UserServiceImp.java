package com.example.Edupulse.user;


import com.example.Edupulse.exception.BadRequestException;
import com.example.Edupulse.exception.ResourceNotFoundException;
import com.example.Edupulse.school.School;
import com.example.Edupulse.school.SchoolRepo;
import com.example.Edupulse.section.SectionRepo;
import com.example.Edupulse.security.CookieBuilder;
import com.example.Edupulse.standard.StandardRepo;
import com.example.Edupulse.user.dto.request.CreateUserRequest;
import com.example.Edupulse.user.dto.request.UserRequest;
import com.example.Edupulse.user.dto.request.LoginRequest;
import com.example.Edupulse.user.parent.Parent;
import com.example.Edupulse.user.parent.ParentRepo;
import com.example.Edupulse.user.student.Student;
import com.example.Edupulse.user.student.StudentRepo;
import com.example.Edupulse.user.teacher.Teacher;
import com.example.Edupulse.user.teacher.TeacherRepo;
import com.example.Edupulse.utils.JwtUtils;
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
    private final ParentRepo parentRepo;
    private final TeacherRepo teacherRepo;
    private final StudentRepo studentRepo;
    private final StandardRepo standardRepo;
    private final SectionRepo sectionRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final CookieBuilder cookieBuilder;
    private final SchoolRepo schoolRepo;
    private final UserResponseFactory userResponseFactory;

    @Override
    @Transactional
    public void registerUser(CreateUserRequest request) {
        if (userRepo.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        School school = schoolRepo.findById(request.getSchoolId())
                .orElseThrow(() -> new ResourceNotFoundException("School not found"));

        // Step 1 — save base user
        User user = User.builder()
                .school(school)
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .phone(request.getPhone())
                .address(request.getAddress())
                .profilePic(request.getProfilePic())
                .build();

        userRepo.save(user);

        // Step 2 — save role-specific profile
        switch (request.getRole()) {
            case STUDENT -> {
                Student student = Student.builder()
                        .user(user)
                        .rollNumber(request.getRollNumber())
                        .admissionDate(request.getAdmissionDate())
                        .build();

                // optionally link standard/section if provided
                if (request.getStandardId() != null) {
                    student.setStandard(
                            standardRepo.findById(request.getStandardId())
                                    .orElseThrow(() -> new ResourceNotFoundException("Standard not found"))
                    );
                }
                if (request.getSectionId() != null) {
                    student.setSection(
                            sectionRepo.findById(request.getSectionId())
                                    .orElseThrow(() -> new ResourceNotFoundException("Section not found"))
                    );
                }
                if (request.getParentId() != null) {
                    User parentUser = userRepo.findById(request.getParentId())
                            .orElseThrow(() -> new ResourceNotFoundException("Parent not found"));
                    Parent parent = parentRepo.findById(parentUser.getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Parent profile not found"));
                    student.setParent(parent);
                }

                studentRepo.save(student);
            }

            case TEACHER -> teacherRepo.save(
                    Teacher.builder()
                            .user(user)
                            .qualification(request.getQualification())
                            .subject(request.getSubject())
                            .joiningDate(request.getJoiningDate())
                            .build()
            );

            case PARENT -> parentRepo.save(
                    Parent.builder()
                            .user(user)
                            .occupation(request.getOccupation())
                            .alternatePhone(request.getAlternatePhone())
                            .build()
            );

            default -> log.debug("No profile table needed for role: {}", request.getRole());
        }
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

    @Override
    public Object getProfile(String userId, String role) {
        UUID id = UUID.fromString(userId);

        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return userResponseFactory.build(user);
    }
}
