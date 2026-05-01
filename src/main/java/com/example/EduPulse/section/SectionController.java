// SectionController.java
package com.example.EduPulse.section;

import com.example.EduPulse.common.ApiResponse;
import com.example.EduPulse.section.dto.SectionRequest;
import com.example.EduPulse.section.dto.SectionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/school/{schoolId}/standard/{standardId}/section")
@RequiredArgsConstructor
public class SectionController {

    private final SectionService sectionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<SectionResponse>>> getAll(
            @PathVariable String standardId) {
        return ResponseEntity.ok(ApiResponse.success(sectionService.getAllByStandard(standardId)));
    }

    @GetMapping("/{sectionId}")
    public ResponseEntity<ApiResponse<SectionResponse>> getById(
            @PathVariable String sectionId) {
        return ResponseEntity.ok(ApiResponse.success(sectionService.getById(sectionId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SectionResponse>> create(
            @PathVariable String standardId,
            @Valid @RequestBody SectionRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(sectionService.create(standardId, request)));
    }

    @PatchMapping("/{sectionId}")
    public ResponseEntity<ApiResponse<SectionResponse>> update(
            @PathVariable String sectionId,
            @Valid @RequestBody SectionRequest request) {
        return ResponseEntity.ok(ApiResponse.success(sectionService.update(sectionId, request)));
    }

    @DeleteMapping("/{sectionId}")
    public ResponseEntity<ApiResponse<String>> delete(
            @PathVariable String sectionId) {
        sectionService.delete(sectionId);
        return ResponseEntity.ok(ApiResponse.success("Section deleted successfully"));
    }
}