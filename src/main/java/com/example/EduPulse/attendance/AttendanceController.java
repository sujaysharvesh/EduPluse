package com.example.EduPulse.attendance;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;


    // Single student
    @PostMapping
    public ResponseEntity<AttendanceDto.Response> mark(
            @Valid @RequestBody AttendanceDto.MarkRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(attendanceService.mark(request));
    }

    // Entire section at once
    @PostMapping("/bulk")
    public ResponseEntity<List<AttendanceDto.Response>> bulkMark(
            @Valid @RequestBody AttendanceDto.BulkMarkRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(attendanceService.bulkMark(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceDto.Response> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(attendanceService.getById(id));
    }

    // GET /api/v1/attendance/student/{studentId}
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AttendanceDto.Response>> getByStudent(
            @PathVariable UUID studentId) {
        return ResponseEntity.ok(attendanceService.getByStudent(studentId));
    }

    // GET /api/v1/attendance/student/{studentId}/range?from=2025-06-01&to=2025-06-30
    @GetMapping("/student/{studentId}/range")
    public ResponseEntity<List<AttendanceDto.Response>> getByStudentAndRange(
            @PathVariable UUID studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(
                attendanceService.getByStudentAndDateRange(studentId, from, to));
    }

    // GET /api/v1/attendance/student/{studentId}/summary?from=2025-06-01&to=2025-06-30
    @GetMapping("/student/{studentId}/summary")
    public ResponseEntity<AttendanceDto.SummaryResponse> getSummary(
            @PathVariable UUID studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(attendanceService.getSummary(studentId, from, to));
    }

    // GET /api/v1/attendance/section/{sectionId}/date/2025-06-10
    @GetMapping("/section/{sectionId}/date/{date}")
    public ResponseEntity<List<AttendanceDto.Response>> getBySectionAndDate(
            @PathVariable UUID sectionId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(attendanceService.getBySectionAndDate(sectionId, date));
    }

    // GET /api/v1/attendance/section/{sectionId}/date/2025-06-10/absent
    @GetMapping("/section/{sectionId}/date/{date}/absent")
    public ResponseEntity<List<AttendanceDto.Response>> getAbsentBySectionAndDate(
            @PathVariable UUID sectionId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(
                attendanceService.getAbsentBySectionAndDate(sectionId, date));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<AttendanceDto.Response> update(
            @PathVariable UUID id,
            @Valid @RequestBody AttendanceDto.UpdateRequest request) {
        return ResponseEntity.ok(attendanceService.update(id, request));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        attendanceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}