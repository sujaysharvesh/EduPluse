package com.example.EduPulse.section;

import com.example.EduPulse.exception.BadRequestException;
import com.example.EduPulse.exception.ResourceNotFoundException;
import com.example.EduPulse.section.dto.SectionRequest;
import com.example.EduPulse.security.AuthContext;
import com.example.EduPulse.section.dto.SectionResponse;
import com.example.EduPulse.standard.Standard;
import com.example.EduPulse.standard.StandardRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SectionServiceImp implements SectionService {

    private final SectionRepo sectionRepo;
    private final StandardRepo standardRepo;
    private final AuthContext authContext;

    @Override
    public List<SectionResponse> getAllByStandard(String standardId) {
        Standard standard = findStandard(standardId);
        verifySchoolAccess(standard);
        return sectionRepo.findAllByStandardId(UUID.fromString(standardId))
                .stream().map(this::mapToResponse).toList();
    }

    @Override
    public SectionResponse getById(String sectionId) {
        Section section = findSection(sectionId);
        verifySchoolAccess(section.getStandard());
        return mapToResponse(section);
    }

    @Override
    public SectionResponse create(String standardId, SectionRequest request) {
        Standard standard = findStandard(standardId);
        verifySchoolAccess(standard);

        if (sectionRepo.existsByNameAndStandardId(request.getName(), UUID.fromString(standardId))) {
            throw new BadRequestException("Section '" + request.getName() + "' already exists in this standard");
        }

        Section section = Section.builder()
                .name(request.getName())
                .standard(standard)
                .build();

        return mapToResponse(sectionRepo.save(section));
    }

    @Override
    public SectionResponse update(String sectionId, SectionRequest request) {
        Section section = findSection(sectionId);
        verifySchoolAccess(section.getStandard());

        if (request.getName() != null) section.setName(request.getName());

        return mapToResponse(sectionRepo.save(section));
    }

    @Override
    public void delete(String sectionId) {
        Section section = findSection(sectionId);
        verifySchoolAccess(section.getStandard());
        sectionRepo.delete(section);
    }

    // ── helpers ─────────────────────────────────────────────
    private Standard findStandard(String id) {
        return standardRepo.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Standard not found"));
    }

    private Section findSection(String id) {
        return sectionRepo.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Section not found"));
    }

    private void verifySchoolAccess(Standard standard) {
        if (authContext.isSuperAdmin()) return;
        String adminSchoolId = authContext.getCurrentUserSchoolId();
        String resourceSchoolId = standard.getSchool().getId().toString();
        if (adminSchoolId == null || !adminSchoolId.equals(resourceSchoolId)) {
            throw new org.springframework.security.access.AccessDeniedException("Access denied");
        }
    }

    private SectionResponse mapToResponse(Section s) {
        return SectionResponse.builder()
                .id(s.getId().toString())
                .name(s.getName())
                .standardId(s.getStandard().getId().toString())
                .standardName(s.getStandard().getName())
                .build();
    }
}