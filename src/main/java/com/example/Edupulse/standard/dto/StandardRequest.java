package com.example.Edupulse.standard.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StandardRequest {

    @NotBlank(message = "Name is required")
    private String name;

}