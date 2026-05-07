package com.example.EduPulse.timeTable;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.time.LocalTime;
import java.util.UUID;


public class TimeTableDto {

    public record CreateRequest(

            @NotNull(message = "Standard ID is required")
            UUID standardId,

            @NotNull(message = "Section ID is required")
            UUID sectionId,

            @NotNull(message = "SubjectTeacher ID is required")
            UUID subjectTeacherId,

            @NotNull(message = "Day of week is required")
            @Min(value = 1, message = "Day must be between 1 (Mon) and 6 (Sat)")
            @Max(value = 6, message = "Day must be between 1 (Mon) and 6 (Sat)")
            Integer dayOfWeek,

            @NotNull(message = "Period number is required")
            @Min(value = 1, message = "Period number must be at least 1")
            Integer periodNumber,

            @NotNull(message = "Start time is required")
            LocalTime startTime,

            @NotNull(message = "End time is required")
            LocalTime endTime

    ) {
    }

    public record UpdateRequest(

            UUID subjectTeacherId,      // nullable — only update if provided

            @Min(value = 1, message = "Day must be between 1 (Mon) and 6 (Sat)")
            @Max(value = 6, message = "Day must be between 1 (Mon) and 6 (Sat)")
            Integer dayOfWeek,

            @Min(value = 1, message = "Period number must be at least 1")
            Integer periodNumber,

            LocalTime startTime,
            LocalTime endTime

    ) {
    }

    public record SubjectTeacherInfo(
            UUID id,
            String teacherName,
            String subjectName
    ) {}

    public record Response(
            String  id,
            String  standardId,
            String  standardName,
            String  sectionId,
            String  sectionName,
            String  academicYear,
            String  subjectTeacherId,
            String  teacherId,
            String  teacherName,
            String  subjectId,
            String  subjectName,
            Integer dayOfWeek,
            Integer periodNumber,
            LocalTime startTime,
            LocalTime endTime
    ) {}

}