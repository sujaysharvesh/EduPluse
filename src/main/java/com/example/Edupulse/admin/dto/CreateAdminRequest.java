package com.example.Edupulse.admin.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SecondaryRow;

import java.util.UUID;

@Getter
@Setter
@Builder
public class CreateAdminRequest {

    private UUID schoolId;

}
