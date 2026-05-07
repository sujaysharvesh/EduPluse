package com.example.EduPulse.exam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExamRepo extends JpaRepository<Exam, UUID> {

    List<Exam> findBySectionIdOrderByStartDateAsc(UUID sectionId);

    List<Exam> findBySectionIdAndAcademicYear(UUID sectionId, String academicYear);

    // All exams by type (e.g. all "Quarterly" exams)
    List<Exam> findByExamTypeId(UUID examTypeId);

    boolean existsByExamTypeIdAndSectionIdAndAcademicYear(
            UUID examTypeId, UUID sectionId, String academicYear);

    boolean existsByExamTypeIdAndSectionIdAndAcademicYearAndIdNot(
            UUID examTypeId, UUID sectionId, String academicYear, UUID id);
}
