package com.example.EduPulse.exam.examMark;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/exam-marks")
@RequiredArgsConstructor
public class ExamMarkController {

    private final ExamMarkService examMarkService;

    @PostMapping
    public ResponseEntity<ExamMarkDto.Response> create(
            @Valid @RequestBody ExamMarkDto.CreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(examMarkService.create(request));
    }

    // POST /api/v1/exam-marks/bulk  — all subjects for one student in one call
    @PostMapping("/bulk")
    public ResponseEntity<List<ExamMarkDto.Response>> bulkCreate(
            @Valid @RequestBody ExamMarkDto.BulkCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(examMarkService.bulkCreate(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamMarkDto.Response> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(examMarkService.getById(id));
    }

    // GET /api/v1/exam-marks/student/{studentId}
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ExamMarkDto.Response>> getByStudent(
            @PathVariable UUID studentId) {
        return ResponseEntity.ok(examMarkService.getByStudent(studentId));
    }

    // GET /api/v1/exam-marks/student/{studentId}/exam/{examId}
    @GetMapping("/student/{studentId}/exam/{examId}")
    public ResponseEntity<List<ExamMarkDto.Response>> getByStudentAndExam(
            @PathVariable UUID studentId,
            @PathVariable UUID examId) {
        return ResponseEntity.ok(examMarkService.getByStudentAndExam(studentId, examId));
    }

    // GET /api/v1/exam-marks/exam/{examId}
    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<ExamMarkDto.Response>> getByExam(@PathVariable UUID examId) {
        return ResponseEntity.ok(examMarkService.getByExam(examId));
    }

    // GET /api/v1/exam-marks/exam/{examId}/subject/{subjectId}
    @GetMapping("/exam/{examId}/subject/{subjectId}")
    public ResponseEntity<List<ExamMarkDto.Response>> getByExamAndSubject(
            @PathVariable UUID examId,
            @PathVariable UUID subjectId) {
        return ResponseEntity.ok(examMarkService.getByExamAndSubject(examId, subjectId));
    }

    // GET /api/v1/exam-marks/report-card?studentId=&sectionId=&year=2025-26
    @GetMapping("/report-card")
    public ResponseEntity<List<ExamMarkDto.Response>> getReportCard(
            @RequestParam UUID   studentId,
            @RequestParam UUID   sectionId,
            @RequestParam String year) {
        return ResponseEntity.ok(examMarkService.getReportCard(studentId, sectionId, year));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExamMarkDto.Response> update(
            @PathVariable UUID id,
            @Valid @RequestBody ExamMarkDto.UpdateRequest request) {
        return ResponseEntity.ok(examMarkService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        examMarkService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
