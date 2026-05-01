package com.example.EduPulse.standard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StandardResponse {

    private String name;
    private String schoolId;

}