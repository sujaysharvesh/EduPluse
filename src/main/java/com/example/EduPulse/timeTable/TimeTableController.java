package com.example.EduPulse.timeTable;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/timetable")
@RequiredArgsConstructor
public class TimeTableController {

    private final TimeTableService timeTableService;


    @PostMapping
    public ResponseEntity<TimeTableDto.Response> create(
            @Valid @RequestBody TimeTableDto.CreateRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(timeTableService.create(request));
    }


    @GetMapping("/{id}")
    public ResponseEntity<TimeTableDto.Response> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(timeTableService.getById(id));
    }

    @GetMapping("/section/{sectionId}")
    public ResponseEntity<List<TimeTableDto.Response>> getBySection(
            @PathVariable UUID sectionId) {
        return ResponseEntity.ok(timeTableService.getBySection(sectionId));
    }

    @GetMapping("/section/{sectionId}/day/{dayOfWeek}")
    public ResponseEntity<List<TimeTableDto.Response>> getBySectionAndDay(
            @PathVariable UUID sectionId,
            @PathVariable Integer dayOfWeek) {
        return ResponseEntity.ok(timeTableService.getBySectionAndDay(sectionId, dayOfWeek));
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<TimeTableDto.Response>> getByTeacher(
            @PathVariable UUID teacherId) {
        return ResponseEntity.ok(timeTableService.getByTeacher(teacherId));
    }

    @GetMapping("/standard/{standardId}")
    public ResponseEntity<List<TimeTableDto.Response>> getByStandard(
            @PathVariable UUID standardId) {
        return ResponseEntity.ok(timeTableService.getByStandard(standardId));
    }

    // GET /api/v1/timetable/section/{sectionId}/day/{dayOfWeek}/first-period
    // Used by attendance module to find the responsible teacher
    @GetMapping("/section/{sectionId}/day/{dayOfWeek}/first-period")
    public ResponseEntity<TimeTableDto.Response> getFirstPeriod(
            @PathVariable UUID sectionId,
            @PathVariable Integer dayOfWeek) {
        return ResponseEntity.ok(timeTableService.getFirstPeriod(sectionId, dayOfWeek));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<TimeTableDto.Response> update(
            @PathVariable UUID id,
            @Valid @RequestBody TimeTableDto.UpdateRequest request) {
        return ResponseEntity.ok(timeTableService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        timeTableService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
