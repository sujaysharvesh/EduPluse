package com.example.EduPulse.exam.examType;

import java.util.List;
import java.util.UUID;

public interface ExamTypeService {

    ExamTypeDto.Response     create(ExamTypeDto.CreateRequest request);
    ExamTypeDto.Response     update(UUID id, ExamTypeDto.UpdateRequest request);
    ExamTypeDto.Response     getById(UUID id);
    List<ExamTypeDto.Response> getAll();
    void                     delete(UUID id);
}