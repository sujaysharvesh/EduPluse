package com.example.EduPulse.exception;

public class ExamTypeException {

    public static class NotFound extends RuntimeException {
        public NotFound(String id) { super("ExamType not found: " + id); }
    }

    public static class DuplicateName extends RuntimeException {
        public DuplicateName(String name) { super("ExamType already exists with name: " + name); }
    }
}
