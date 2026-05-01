package com.example.EduPulse.school;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SchoolRepo extends JpaRepository<School, UUID> {

//    School createSchool(CreateSchoolRequest request);

    Optional<School> findById(UUID schoolId);

    /*
    * delete
    * update
    * */




}
