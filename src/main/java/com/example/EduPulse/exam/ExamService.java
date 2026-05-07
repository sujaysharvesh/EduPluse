package com.example.EduPulse.exam;

import java.util.List;
import java.util.UUID;

public interface ExamService {
    ExamDto.Response       create(ExamDto.CreateRequest request);
    ExamDto.Response       update(UUID id, ExamDto.UpdateRequest request);
    ExamDto.Response       getById(UUID id);
    List<ExamDto.Response> getBySection(UUID sectionId);
    List<ExamDto.Response> getBySectionAndYear(UUID sectionId, String academicYear);
    List<ExamDto.Response> getByExamType(UUID examTypeId);
    void                   delete(UUID id);
}