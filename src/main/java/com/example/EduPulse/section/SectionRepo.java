package com.example.EduPulse.section;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface SectionRepo extends JpaRepository<Section, UUID> {
    List<Section> findAllByStandardId(UUID standardId);
    boolean existsByNameAndStandardId(String name, UUID standardId);
}
