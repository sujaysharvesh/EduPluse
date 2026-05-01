package com.example.EduPulse.standard;

import com.example.EduPulse.standard.dto.StandardRequest;
import com.example.EduPulse.standard.dto.StandardResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StandardService {

    List<StandardResponse> getAllBySchool(String schoolId);
    StandardResponse create(String schoolId, StandardRequest standardRequest);
    StandardResponse getById(String standardId);
    StandardResponse update(String schoolId, StandardRequest standardRequest);
    void delete(String standardId);


}
