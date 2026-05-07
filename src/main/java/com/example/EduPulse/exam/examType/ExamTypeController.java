package com.example.EduPulse.exam.examType;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/exam-types")
@RequiredArgsConstructor
public class ExamTypeController {

    private final ExamTypeService examTypeService;

    @PostMapping
    public ResponseEntity<ExamTypeDto.Response> create(
            @Valid @RequestBody ExamTypeDto.CreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(examTypeService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<ExamTypeDto.Response>> getAll() {
        return ResponseEntity.ok(examTypeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamTypeDto.Response> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(examTypeService.getById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExamTypeDto.Response> update(
            @PathVariable UUID id,
            @Valid @RequestBody ExamTypeDto.UpdateRequest request) {
        return ResponseEntity.ok(examTypeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        examTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
