package com.example.EduPulse.school;

import com.example.EduPulse.common.ApiResponse;
import com.example.EduPulse.school.dto.SchoolRequest;
import com.example.EduPulse.school.dto.SchoolResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/school")
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SchoolResponse>>> getAllSchools() {
        List<SchoolResponse> schools = schoolService.getAllSchool();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(schools));
    }

    @GetMapping("/{schoolId}")
    public ResponseEntity<ApiResponse<SchoolResponse>> getSchoolById(
            @PathVariable String schoolId) {
        SchoolResponse school = schoolService.getSchoolById(schoolId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(school));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createSchool(
            @Valid @RequestBody SchoolRequest request) {
        schoolService.createSchool(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("School created successfully"));
    }

    @PatchMapping("/{schoolId}")
    public ResponseEntity<ApiResponse<String>> updateSchool(
            @PathVariable String schoolId,
            @Valid @RequestBody SchoolRequest request) {
        schoolService.updateSchool(schoolId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("School updated successfully"));
    }

    @DeleteMapping("/{schoolId}")
    public ResponseEntity<ApiResponse<String>> deleteSchool(
            @PathVariable String schoolId) {
        schoolService.deleteSchool(schoolId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("School deleted successfully"));
    }
}