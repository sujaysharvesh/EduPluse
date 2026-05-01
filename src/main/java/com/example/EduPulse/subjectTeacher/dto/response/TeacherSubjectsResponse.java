package com.example.EduPulse.subjectTeacher.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class TeacherSubjectsResponse {

    private UUID teacherId;
    private String teacherName;

    private UUID subjectId;
    private String subjectName;

    private UUID standardId;
    private String standard;

}