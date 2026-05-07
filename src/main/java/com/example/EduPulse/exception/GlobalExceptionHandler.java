package com.example.EduPulse.exception;


import com.example.EduPulse.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(exception.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<String>> handleBadRequestException(BadRequestException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation failed");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(errorMessage));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<String>> handleUnauthorizedException(
            UnauthorizedException exception) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGenericException(Exception ex) {

        log.error("Unexpected error", ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Internal server error"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<String>> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(TimeTableException.NotFound.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(
            TimeTableException.NotFound ex) {

        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(TimeTableException.SlotConflict.class)
    public ResponseEntity<ApiResponse<Object>> handleSlotConflict(TimeTableException.SlotConflict ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(TimeTableException.InvalidTime.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidTime(TimeTableException.InvalidTime ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(AttendanceException.NotFound.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidAttendance(AttendanceException.NotFound ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(AttendanceException.AlreadyMarked.class)
    public ResponseEntity<ApiResponse<Object>> handleAlreadyMarked(AttendanceException.AlreadyMarked ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(AttendanceException.FutureDateNotAllowed.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidAttendance(AttendanceException.FutureDateNotAllowed ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ExamTypeException.NotFound.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(ExamTypeException.NotFound ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ExamTypeException.DuplicateName.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(ExamTypeException.DuplicateName ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }


    private ResponseEntity<ApiResponse<Object>> build(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(ApiResponse.error(message));
    }

}
