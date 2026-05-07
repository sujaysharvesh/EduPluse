package com.example.EduPulse.exam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @PostMapping
    public ResponseEntity<ExamDto.Response> create(
            @Valid @RequestBody ExamDto.CreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(examService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamDto.Response> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(examService.getById(id));
    }

    // GET /api/v1/exams/section/{sectionId}
    @GetMapping("/section/{sectionId}")
    public ResponseEntity<List<ExamDto.Response>> getBySection(@PathVariable UUID sectionId) {
        return ResponseEntity.ok(examService.getBySection(sectionId));
    }

    // GET /api/v1/exams/section/{sectionId}?year=2025-26
    @GetMapping("/section/{sectionId}/year")
    public ResponseEntity<List<ExamDto.Response>> getBySectionAndYear(
            @PathVariable UUID sectionId,
            @RequestParam String year) {
        return ResponseEntity.ok(examService.getBySectionAndYear(sectionId, year));
    }

    // GET /api/v1/exams/type/{examTypeId}
    @GetMapping("/type/{examTypeId}")
    public ResponseEntity<List<ExamDto.Response>> getByExamType(@PathVariable UUID examTypeId) {
        return ResponseEntity.ok(examService.getByExamType(examTypeId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExamDto.Response> update(
            @PathVariable UUID id,
            @Valid @RequestBody ExamDto.UpdateRequest request) {
        return ResponseEntity.ok(examService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        examService.delete(id);
        return ResponseEntity.noContent().build();
    }
}