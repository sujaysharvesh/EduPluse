package com.example.EduPulse.exam.examMark;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public class ExamMarkDto {

    public record CreateRequest(
            @NotNull(message = "Student ID is required")
            UUID studentId,

            @NotNull(message = "Exam ID is required")
            UUID examId,

            @NotNull(message = "Subject ID is required")
            UUID subjectId,

            @Min(value = 0, message = "Obtained mark cannot be negative")
            Integer obtainedMark,

            String  grade,
            Boolean isAbsent,
            UUID    enteredBy
    ) {}

    // Bulk entry — enter all subject marks for one student at once
    public record BulkCreateRequest(
            @NotNull UUID examId,
            @NotNull UUID studentId,
            @NotNull UUID enteredBy,
            @NotNull List<SubjectMarkEntry> marks
    ) {}

    public record SubjectMarkEntry(
            @NotNull UUID    subjectId,
            Integer          obtainedMark,
            String           grade,
            Boolean          isAbsent
    ) {}

    record UpdateRequest(
            Integer obtainedMark,
            String  grade,
            Boolean isAbsent
    ) {}

    record Response(
            String  id,
            String  studentId,
            String  studentName,
            String  rollNumber,
            String  examId,
            String  examTypeName,
            String  academicYear,
            Integer maxMark,
            String  subjectId,
            String  subjectName,
            Integer obtainedMark,
            String  grade,
            Boolean isAbsent,
            Double  percentage,
            String  enteredBy
    ) {}
}