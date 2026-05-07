package com.example.EduPulse.exam.examMark;

import java.util.List;
import java.util.UUID;

public interface ExamMarkService {
    ExamMarkDto.Response       create(ExamMarkDto.CreateRequest request);
    List<ExamMarkDto.Response> bulkCreate(ExamMarkDto.BulkCreateRequest request);
    ExamMarkDto.Response       update(UUID id, ExamMarkDto.UpdateRequest request);
    ExamMarkDto.Response       getById(UUID id);
    List<ExamMarkDto.Response> getByStudent(UUID studentId);
    List<ExamMarkDto.Response> getByStudentAndExam(UUID studentId, UUID examId);
    List<ExamMarkDto.Response> getByExam(UUID examId);
    List<ExamMarkDto.Response> getByExamAndSubject(UUID examId, UUID subjectId);
    List<ExamMarkDto.Response> getReportCard(UUID studentId, UUID sectionId, String academicYear);
    void                       delete(UUID id);
}
