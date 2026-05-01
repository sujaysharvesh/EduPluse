package com.example.EduPulse.standard;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface StandardRepo extends JpaRepository<Standard, UUID> {

    List<Standard> findAllBySchoolId(UUID schoolId);
    boolean existsByNameAndSchoolId(String name, UUID schoolId);

}
