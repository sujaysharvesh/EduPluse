package com.example.EduPulse.exception;

public class TimeTableException {

    public static class NotFound extends RuntimeException {
        public NotFound(String id) {
            super("Timetable entry not found with id: " + id);
        }
    }

    public static class SlotConflict extends RuntimeException {
        public SlotConflict(String sectionId, int day, int period) {
            super("A timetable slot already exists for section " + sectionId
                    + " on day " + day + " period " + period);
        }
    }

    public static class InvalidTime extends RuntimeException {
        public InvalidTime() {
            super("End time must be after start time");
        }
    }

}
