package com.example.EduPulse.exception;

import java.time.LocalDate;

public class AttendanceException {

    public static class NotFound extends RuntimeException {
        public NotFound(String id) {
            super("Attendance record not found with id: " + id);
        }
    }

    public static class AlreadyMarked extends RuntimeException {
        public AlreadyMarked(String studentId, LocalDate date) {
            super("Attendance already marked for student " + studentId + " on " + date);
        }
    }

    public static class FutureDateNotAllowed extends RuntimeException {
        public FutureDateNotAllowed() {
            super("Attendance cannot be marked for a future date");
        }
    }
}
