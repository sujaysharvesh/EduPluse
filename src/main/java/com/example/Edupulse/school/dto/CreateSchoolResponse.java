package com.example.Edupulse.school.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class CreateSchoolResponse {

    private String adminMailId;
    private String password;

}
