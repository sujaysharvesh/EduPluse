package com.example.EduPulse.user.dto.response;

import com.example.EduPulse.user.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class TeacherResponse {
    private String id;
    private String username;
    private String email;
    private UserRole role;
    private String phone;
    private String profilePic;
    private String schoolId;
    private String schoolName;

    // teacher specific
    private String qualification;
    private String subject;
    private LocalDate joiningDate;
}
