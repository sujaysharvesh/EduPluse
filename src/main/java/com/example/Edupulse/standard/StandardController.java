// StandardController.java
package com.example.Edupulse.standard;

import com.example.Edupulse.common.ApiResponse;
import com.example.Edupulse.standard.dto.StandardRequest;
import com.example.Edupulse.standard.dto.StandardResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/school/{schoolId}/standard")
@RequiredArgsConstructor
public class StandardController {

    private final StandardService standardService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<StandardResponse>>> getAll(
            @PathVariable String schoolId) {
        return ResponseEntity.ok(ApiResponse.success(standardService.getAllBySchool(schoolId)));
    }

    @GetMapping("/{standardId}")
    public ResponseEntity<ApiResponse<StandardResponse>> getById(
            @PathVariable String schoolId,
            @PathVariable String standardId) {
        return ResponseEntity.ok(ApiResponse.success(standardService.getById(standardId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StandardResponse>> create(
            @PathVariable String schoolId,
            @Valid @RequestBody StandardRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(standardService.create(schoolId, request)));
    }

    @PatchMapping("/{standardId}")
    public ResponseEntity<ApiResponse<StandardResponse>> update(
            @PathVariable String schoolId,
            @PathVariable String standardId,
            @Valid @RequestBody StandardRequest request) {
        return ResponseEntity.ok(ApiResponse.success(standardService.update(standardId, request)));
    }

    @DeleteMapping("/{standardId}")
    public ResponseEntity<ApiResponse<String>> delete(
            @PathVariable String schoolId,
            @PathVariable String standardId) {
        standardService.delete(standardId);
        return ResponseEntity.ok(ApiResponse.success("Standard deleted successfully"));
    }
}