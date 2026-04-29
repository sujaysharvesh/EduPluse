package com.example.Edupulse.section.dto;

import lombok.*;
import java.time.Instant;

@Getter @Setter @Builder
public class SectionResponse {

    private String id;
    private String name;
    private String standardId;
    private String standardName;

}
