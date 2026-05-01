package com.example.EduPulse.school.dto;

import com.example.EduPulse.common.enums.SchoolStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolRequest {

    @NotBlank(message = "School name is required")
    @Size(max = 100)
    private String schoolName;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "City is required")
    @Size(max = 50)
    private String city;

    @NotBlank(message = "State is required")
    @Size(max = 50)
    private String state;

    @NotBlank(message = "Pincode is required")
    @Pattern(
            regexp = "^[0-9]{6}$",
            message = "Invalid pincode"
    )
    private String pincode;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[+]?[0-9]{10,15}$",
            message = "Invalid phone number"
    )
    private String phone;

    private boolean isActive;
}