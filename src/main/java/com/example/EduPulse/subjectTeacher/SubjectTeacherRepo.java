package com.example.EduPulse.subjectTeacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SubjectTeacherRepo extends JpaRepository<SubjectTeacher, UUID> {

    List<SubjectTeacher> findAllByTeacher_Id(UUID teacherId);

    boolean existsBySubject_Id(UUID subjectId);

    @Query("""
    SELECT st FROM SubjectTeacher st
    JOIN FETCH st.teacher t
    JOIN FETCH t.user
    JOIN FETCH st.subject s
    JOIN FETCH s.standard
    WHERE t.id = :teacherId
""")
    List<SubjectTeacher> findAllWithDetailsByTeacherId(UUID teacherId);

    boolean existsByTeacher_IdAndSubject_Id(UUID teacherId, UUID subjectId);

    void deleteByTeacher_IdAndSubject_Id(UUID teacherId, UUID subjectId);

}
