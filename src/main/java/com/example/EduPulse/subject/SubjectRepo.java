package com.example.EduPulse.subject;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubjectRepo extends JpaRepository<Subject, UUID> {


}
