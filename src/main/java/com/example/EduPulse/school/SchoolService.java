package com.example.EduPulse.school;


import com.example.EduPulse.school.dto.SchoolRequest;
import com.example.EduPulse.school.dto.SchoolResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SchoolService {

    List<SchoolResponse> getAllSchool();

    SchoolResponse getSchoolById(String schoolId);

    void createSchool(SchoolRequest schoolRequest);

    void updateSchool(String schoolId, SchoolRequest request);

    void deleteSchool(String schoolId);





}
