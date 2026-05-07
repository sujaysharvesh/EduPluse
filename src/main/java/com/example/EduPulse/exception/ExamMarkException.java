package com.example.EduPulse.exception;

public class ExamMarkException {

    public static class NotFound extends RuntimeException {
        public NotFound(String id) { super("ExamMark not found: " + id); }
    }

    public static class AlreadyEntered extends RuntimeException {
        public AlreadyEntered(String studentId, String subjectId) {
            super("Mark already entered for student " + studentId
                    + " and subject " + subjectId);
        }
    }

    public static class ExceedsMaxMark extends RuntimeException {
        public ExceedsMaxMark(int obtained, int max) {
            super("Obtained mark " + obtained + " exceeds max mark " + max);
        }
    }
}
