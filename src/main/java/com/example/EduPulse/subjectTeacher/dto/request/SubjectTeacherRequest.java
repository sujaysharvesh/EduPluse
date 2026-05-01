package com.example.EduPulse.subjectTeacher.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class SubjectTeacherRequest {

    @NotNull(message = "Teacher ID is required")
    private UUID teacherId;

    @NotNull(message = "Subject ID is required")
    private UUID subjectId;


}
