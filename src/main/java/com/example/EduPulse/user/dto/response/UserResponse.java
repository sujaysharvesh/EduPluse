package com.example.EduPulse.user.dto.response;


import com.example.EduPulse.user.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class UserResponse {
    private String username;
    private String email;
    private UserRole role;
    private String phone;
    private String address;
    private String profilePic;
    private String schoolName;
}
