package com.example.Edupulse.user.dto;


import com.example.Edupulse.common.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UserResponse {

    private String username;

    private String email;

    private UserRole role;

    private String profilePic;

    private String phone;

    private String address;

}
