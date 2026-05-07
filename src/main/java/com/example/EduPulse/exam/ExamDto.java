package com.example.EduPulse.exam;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public class ExamDto {

    public record CreateRequest(
            @NotNull(message = "ExamType ID is required")
            UUID examTypeId,

            @NotNull(message = "Section ID is required")
            UUID sectionId,

            @NotBlank(message = "Academic year is required")
            String academicYear,

            LocalDate startDate,
            LocalDate endDate
    ) {}

    record UpdateRequest(
            UUID      examTypeId,
            String    academicYear,
            LocalDate startDate,
            LocalDate endDate
    ) {}

    public record Response(
            String    id,
            String    examTypeId,
            String    examTypeName,
            Integer   maxMark,
            String    sectionId,
            String    sectionName,
            String    standardName,
            String    academicYear,
            LocalDate startDate,
            LocalDate endDate
    ) {}
}
