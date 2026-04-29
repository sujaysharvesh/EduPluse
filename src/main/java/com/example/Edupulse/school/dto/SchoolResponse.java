package com.example.Edupulse.school.dto;


import com.example.Edupulse.common.enums.SchoolStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Builder
@Setter
@Getter
public class SchoolResponse {

    private String schoolName;

    private String address;

    private String city;

    private String state;

    private String pincode;

    private String phone;

    private String email;

    private SchoolStatus status;



}
