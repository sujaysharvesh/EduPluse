package com.example.EduPulse.section.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @Builder
public class SectionRequest {

    @NotBlank(message = "Name is required")
    private String name;

}
