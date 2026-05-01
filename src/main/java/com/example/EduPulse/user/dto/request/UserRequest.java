package com.example.EduPulse.user.dto.request;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserRequest {

    @NotBlank(message = "Username is required")
    @Size(max = 100)
    private String username;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotNull(message = "RoleId is required")
    private Integer roleId;

    @NotNull(message = "SchoolId is required")
    private UUID schoolId;

    private String profilePic;

    @Pattern(
            regexp = "^[+]?[0-9]{10,15}$",
            message = "Invalid phone number"
    )
    private String phone;

    private String address;

}
