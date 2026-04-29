package com.example.Edupulse.section;


import com.example.Edupulse.section.dto.SectionRequest;
import com.example.Edupulse.section.dto.SectionResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SectionService {


    List<SectionResponse> getAllByStandard(String standardId);
    SectionResponse getById(String sectionId);

    SectionResponse create(String standardId, SectionRequest sectionRequest);
    SectionResponse update(String standardId, SectionRequest sectionRequest);

    void delete(String sectionId);
}
