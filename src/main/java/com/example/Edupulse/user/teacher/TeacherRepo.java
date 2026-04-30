package com.example.Edupulse.user.teacher;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, UUID> {

    Optional<Teacher> findByUserId(UUID userId);
}
