package com.example.Edupulse.standard;

import com.example.Edupulse.exception.BadRequestException;
import com.example.Edupulse.exception.ResourceNotFoundException;
import com.example.Edupulse.school.School;
import com.example.Edupulse.school.SchoolRepo;
import com.example.Edupulse.security.AuthContext;
import com.example.Edupulse.standard.dto.StandardRequest;
import com.example.Edupulse.standard.dto.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StandardServiceImp implements StandardService {

    private final StandardRepo standardRepo;
    private final SchoolRepo schoolRepo;
    private final AuthContext authContext;



    @Override
    public List<StandardResponse> getAllBySchool(String schoolId) {

        UUID schoolUUID = UUID.fromString(schoolId);

        if (!schoolRepo.existsById(schoolUUID)) {
            throw new ResourceNotFoundException("School not found with id: " + schoolId);
        }
        verifySchoolAccess(schoolId);

        return standardRepo.findAllBySchoolId(schoolUUID)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public StandardResponse getById(String standardId) {
        Standard standard = findStandard(standardId);
        verifySchoolAccess(standard.getSchool().getId().toString());
        return mapToResponse(standard);
    }

    @Override
    public StandardResponse create(String schoolId, StandardRequest request) {
        verifySchoolAccess(schoolId);

        if (standardRepo.existsByNameAndSchoolId(request.getName(), UUID.fromString(schoolId))) {
            throw new BadRequestException("Standard '" + request.getName() + "' already exists in this school");
        }

        School school = schoolRepo.findById(UUID.fromString(schoolId))
                .orElseThrow(() -> new ResourceNotFoundException("School not found"));

        Standard standard = Standard.builder()
                .name(request.getName())
                .school(school)
                .build();

        return mapToResponse(standardRepo.save(standard));
    }

    @Override
    public StandardResponse update(String standardId, StandardRequest request) {
        Standard standard = findStandard(standardId);
        verifySchoolAccess(standard.getSchool().getId().toString());

        if (request.getName() != null)        standard.setName(request.getName());

        return mapToResponse(standardRepo.save(standard));
    }

    @Override
    public void delete(String standardId) {
        Standard standard = findStandard(standardId);
        verifySchoolAccess(standard.getSchool().getId().toString());
        standardRepo.delete(standard);
    }

    private Standard findStandard(String id) {
        return standardRepo.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Standard not found"));
    }

    private void verifySchoolAccess(String schoolId) {
        if (authContext.isSuperAdmin()) return;
        String adminSchoolId = authContext.getCurrentUserSchoolId();
        if (adminSchoolId == null || !adminSchoolId.equals(schoolId)) {
            throw new org.springframework.security.access.AccessDeniedException("Access denied");
        }
    }

    private StandardResponse mapToResponse(Standard s) {
        return StandardResponse.builder()
                .name(s.getName())
                .schoolId(s.getSchool().getId().toString())
                .build();
    }
}