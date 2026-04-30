package com.example.Edupulse.user.dto.response;

import com.example.Edupulse.common.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class StudentResponse {

    // base
    private String id;
    private String username;
    private String email;
    private UserRole role;
    private String phone;
    private String profilePic;
    private String schoolName;

    // student specific
    private String rollNumber;
    private LocalDate admissionDate;
    private String standardName;
    private String sectionName;

}
