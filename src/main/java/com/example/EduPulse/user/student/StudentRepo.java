package com.example.EduPulse.user.student;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepo extends JpaRepository<Student, UUID> {

    Optional<Student> findByUserId(UUID userId);
    List<Student> findAllByParent_Id(UUID parentId);

    @Query("""
    SELECT s FROM Student s
    JOIN FETCH s.user u
    JOIN FETCH u.school
    LEFT JOIN FETCH s.standard
    LEFT JOIN FETCH s.section
    WHERE s.parent.id = :parentId
""")
    List<Student> findAllWithDetailsByParentId(UUID parentId);
}
