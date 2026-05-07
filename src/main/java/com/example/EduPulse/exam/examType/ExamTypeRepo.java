package com.example.EduPulse.exam.examType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExamTypeRepo extends JpaRepository<ExamType, UUID> {

    boolean existsByExamName(String examName);

    boolean existsByExamNameAndIdNot(String examName, UUID id);
}
