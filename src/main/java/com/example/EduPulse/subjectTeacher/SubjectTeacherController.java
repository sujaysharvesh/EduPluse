package com.example.EduPulse.subjectTeacher;


import com.example.EduPulse.common.ApiResponse;
import com.example.EduPulse.subjectTeacher.dto.request.SubjectTeacherRequest;
import com.example.EduPulse.subjectTeacher.dto.response.TeacherSubjectsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subject-teacher")
@RequiredArgsConstructor
public class SubjectTeacherController {

    private final SubjectTeacherService subjectTeacherService;


    @PostMapping
    public ResponseEntity<ApiResponse<String>> assignTeacherToSubject(
            @Valid @RequestBody SubjectTeacherRequest request
    ) {

        subjectTeacherService.assign(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Teacher assigned to subject successfully"));
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<ApiResponse<List<TeacherSubjectsResponse>>> getTeacherSubjects(
            @PathVariable String teacherId
    ) {

        List<TeacherSubjectsResponse> response =
                subjectTeacherService.teacherSubjects(teacherId);

        return ResponseEntity.ok(
                ApiResponse.success("Teacher subjects fetched successfully", response)
        );
    }

    @DeleteMapping("/teacher/{teacherId}/subject/{subjectId}")
    public ResponseEntity<ApiResponse<String>> removeTeacherSubject(
            @PathVariable String teacherId,
            @PathVariable String subjectId
    ) {

        subjectTeacherService.removeTeacherSubject(teacherId, subjectId);

        return ResponseEntity.ok(
                ApiResponse.success("Teacher subject mapping removed successfully")
        );
    }


}
