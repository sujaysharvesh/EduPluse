package com.example.EduPulse.exam.examMark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExamMarkRepository extends JpaRepository<ExamMark, UUID> {

    // All marks for a student
    List<ExamMark> findByStudentIdOrderByExamStartDateAsc(UUID studentId);

    // All marks for a student in a specific exam
    List<ExamMark> findByStudentIdAndExamId(UUID studentId, UUID examId);

    // All marks for an entire exam (report card view)
    @Query("""
            SELECT em FROM ExamMark em
            WHERE em.exam.id = :examId
            ORDER BY em.student.rollNumber ASC, em.subject.name ASC
            """)
    List<ExamMark> findByExamId(@Param("examId") UUID examId);

    // All marks for a specific subject in an exam
    List<ExamMark> findByExamIdAndSubjectId(UUID examId, UUID subjectId);

    // Duplicate check
    boolean existsByStudentIdAndExamIdAndSubjectId(UUID studentId, UUID examId, UUID subjectId);

    boolean existsByStudentIdAndExamIdAndSubjectIdAndIdNot(
            UUID studentId, UUID examId, UUID subjectId, UUID id);

    // Report card: all marks for a student across all exams in a section+year
    @Query("""
            SELECT em FROM ExamMark em
            WHERE em.student.id = :studentId
            AND em.exam.section.id = :sectionId
            AND em.exam.academicYear = :academicYear
            ORDER BY em.exam.startDate ASC, em.subject.name ASC
            """)
    List<ExamMark> findReportCard(
            @Param("studentId")    UUID studentId,
            @Param("sectionId")    UUID sectionId,
            @Param("academicYear") String academicYear);
}
