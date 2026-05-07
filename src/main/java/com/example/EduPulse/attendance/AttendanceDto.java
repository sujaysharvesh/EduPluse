package com.example.EduPulse.attendance;


import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

class AttendanceDto {

    record MarkRequest(
            @NotNull(message = "Student ID is required")
            UUID studentId,

            @NotNull(message = "Timetable ID is required")
            UUID timeTableId,

            @NotNull(message = "Date is required")
            LocalDate date,

            @NotNull(message = "isPresent is required")
            Boolean isPresent
    ) {}

    // Bulk mark — entire section at once
    record BulkMarkRequest(
            @NotNull(message = "Timetable ID is required")
            UUID timeTableId,

            @NotNull(message = "Date is required")
            LocalDate date,

            @NotNull(message = "Entries are required")
            List<BulkEntry> entries
    ) {}

    record BulkEntry(
            @NotNull UUID studentId,
            @NotNull Boolean isPresent
    ) {}

    // Update a single record
    record UpdateRequest(
            @NotNull(message = "isPresent is required")
            Boolean isPresent
    ) {}

    // Response
    record Response(
            String    id,
            String    studentId,
            String    studentName,
            String    rollNumber,
            String    sectionId,
            String    sectionName,
            String    standardName,
            String    timeTableId,
            String    subjectName,
            Integer   periodNumber,
            Integer   dayOfWeek,
            Boolean   isPresent,
            LocalDate date
    ) {}

    // Attendance summary for a student
    record SummaryResponse(
            String    studentId,
            String    studentName,
            LocalDate from,
            LocalDate to,
            long      totalDays,
            long      presentDays,
            long      absentDays,
            double    attendancePercentage
    ) {}
}
