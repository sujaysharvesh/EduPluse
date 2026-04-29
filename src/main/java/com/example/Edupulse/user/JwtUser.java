package com.example.Edupulse.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class JwtUser {

    private String id;

    private String username;

    private String email;

    private String role;

    private String schoolId;
}
