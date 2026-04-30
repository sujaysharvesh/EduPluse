package com.example.Edupulse.user.dto.request;

import com.example.Edupulse.common.enums.UserRole;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
public class CreateUserRequest {

    @NotBlank(message = "Username is required")
    @Size(max = 100, message = "Username cannot exceed 100 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotNull(message = "Role is required")
    private UserRole role;

    @NotNull(message = "School ID is required")
    private UUID schoolId;

    @Pattern(
            regexp = "^[+]?[0-9]{10,15}$",
            message = "Invalid phone number"
    )
    private String phone;

    @Size(max = 500, message = "Address cannot exceed 500 characters")
    private String address;

    private String profilePic;

    //Student fields
    private UUID standardId;
    private UUID sectionId;
    private UUID parentId;

    @Size(max = 20, message = "Roll number cannot exceed 20 characters")
    private String rollNumber;

    private LocalDate admissionDate;

    //Teacher fields
    @Size(max = 100, message = "Qualification cannot exceed 100 characters")
    private String qualification;

    @Size(max = 100, message = "Subject cannot exceed 100 characters")
    private String subject;

    private LocalDate joiningDate;

    //Parent fields
    @Size(max = 100, message = "Occupation cannot exceed 100 characters")
    private String occupation;

    @Pattern(
            regexp = "^[+]?[0-9]{10,15}$",
            message = "Invalid alternate phone number"
    )
    private String alternatePhone;

//    // Staff fields
//    @Size(max = 100, message = "Department cannot exceed 100 characters")
//    private String department;
//
//    @Size(max = 100, message = "Designation cannot exceed 100 characters")
//    private String designation;
}