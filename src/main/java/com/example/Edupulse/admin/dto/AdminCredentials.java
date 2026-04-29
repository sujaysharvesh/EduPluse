package com.example.Edupulse.admin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class AdminCredentials {

    private String username;

    private String password;
}
