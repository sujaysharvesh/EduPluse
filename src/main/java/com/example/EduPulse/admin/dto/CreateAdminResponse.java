package com.example.EduPulse.admin.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateAdminResponse {

    private String adminName;
    private String adminPassword;


}
