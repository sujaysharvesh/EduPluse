package com.example.Edupulse.admin.dto;


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
