package com.example.EduPulse.exam.examType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ExamTypeDto {

    public record CreateRequest(
            @NotBlank(message = "Exam name is required")
            String examName,

            @NotNull(message = "Max mark is required")
            @Min(value = 1, message = "Max mark must be at least 1")
            Integer maxMark
    ) {}

    record UpdateRequest(
            String  examName,

            @Min(value = 1, message = "Max mark must be at least 1")
            Integer maxMark
    ) {}

    record Response(
            String  id,
            String  examName,
            Integer maxMark
    ) {}
}
