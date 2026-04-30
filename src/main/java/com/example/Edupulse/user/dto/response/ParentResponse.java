package com.example.Edupulse.user.dto.response;

import com.example.Edupulse.common.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ParentResponse {

    private String username;
    private String email;
    private UserRole role;
    private String phone;
    private String profilePic;
    private List<StudentResponse> kids;

    // parent specific
    private String occupation;
    private String alternatePhone;
}
