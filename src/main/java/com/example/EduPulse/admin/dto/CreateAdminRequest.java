package com.example.EduPulse.admin.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class CreateAdminRequest {

    private UUID schoolId;

}
