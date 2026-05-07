package com.example.EduPulse.exception;


public class ExamException {

    public static class NotFound extends RuntimeException {
        public NotFound(String id) { super("Exam not found: " + id); }
    }

    public static class DuplicateExam extends RuntimeException {
        public DuplicateExam(String sectionId, String year) {
            super("Exam of this type already exists for section "
                    + sectionId + " in year " + year);
        }
    }

    public static class InvalidDateRange extends RuntimeException {
        public InvalidDateRange() { super("End date must be after start date"); }
    }
}
