package com.example.Edupulse.school;


import com.example.Edupulse.common.enums.SchoolStatus;
import com.example.Edupulse.exception.AccessDeniedException;
import com.example.Edupulse.exception.BadRequestException;
import com.example.Edupulse.exception.ResourceNotFoundException;
import com.example.Edupulse.school.dto.SchoolRequest;
import com.example.Edupulse.school.dto.SchoolResponse;
import com.example.Edupulse.security.AuthContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class SchoolServiceImp implements SchoolService{


    private final SchoolRepo schoolRepo;
    private final AuthContext authContext;

    @Autowired
    public SchoolServiceImp(SchoolRepo schoolRepo, AuthContext authContext) {
        this.schoolRepo = schoolRepo;
        this.authContext = authContext;
    }

    @Override
    public List<SchoolResponse> getAllSchool() {

        if (!authContext.isSuperAdmin()) {
            throw new AccessDeniedException("Don't have access fetch all school data");
        }

        return schoolRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public SchoolResponse getSchoolById(String schoolId) {
        verifySchoolAccess(schoolId);

        School school = schoolRepo.findById(UUID.fromString(schoolId))
                .orElseThrow(() -> new ResourceNotFoundException("School not found with id: " + schoolId));
        return mapToResponse(school);
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public void createSchool(SchoolRequest request) {

        School school = School.builder()
                .schoolName(request.getSchoolName())
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .pincode(request.getPincode())
                .phone(request.getPhone())
                .status(SchoolStatus.ACTIVE)
                .build();

        schoolRepo.save(school);
    }

    @Override
    public void updateSchool(String schoolId, SchoolRequest request) {
        verifySchoolAccess(schoolId);

        School school = schoolRepo.findById(UUID.fromString(schoolId))
                .orElseThrow(() -> new ResourceNotFoundException("School not found with id: " + schoolId));

        if (request.getSchoolName() != null) school.setSchoolName(request.getSchoolName());
        if (request.getAddress() != null)    school.setAddress(request.getAddress());
        if (request.getCity() != null)       school.setCity(request.getCity());
        if (request.getState() != null)      school.setState(request.getState());
        if (request.getPincode() != null)    school.setPincode(request.getPincode());
        if (request.getPhone() != null)      school.setPhone(request.getPhone());
        if (request.getSchoolStatus() != null)     school.setStatus(request.getSchoolStatus());

        schoolRepo.save(school);

        return;
    }

    @Override
    public void deleteSchool(String schoolId) {
        verifySchoolAccess(schoolId);

        School school = schoolRepo.findById(UUID.fromString(schoolId))
                .orElseThrow(() -> new ResourceNotFoundException("School not found with id: " + schoolId));
        schoolRepo.delete(school);
    }

    private void verifySchoolAccess(String schoolId) {
        // SUPER_ADMIN can access any school
        if (authContext.isSuperAdmin()) return;

        String adminSchoolId = authContext.getCurrentUserSchoolId();

        if (adminSchoolId == null || !adminSchoolId.equals(schoolId)) {
            throw new AccessDeniedException("You do not have access to this school");
        }

    }

    private SchoolResponse mapToResponse(School school) {
        return SchoolResponse.builder()
                .schoolName(school.getSchoolName())
                .address(school.getAddress())
                .city(school.getCity())
                .state(school.getState())
                .pincode(school.getPincode())
                .phone(school.getPhone())
                .status(school.getStatus())
                .build();
    }
}
