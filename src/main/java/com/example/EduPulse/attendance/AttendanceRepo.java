package com.example.EduPulse.attendance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, UUID> {

    // All attendance records for a student
    List<Attendance> findByStudentIdOrderByDateDesc(UUID studentId);

    // All attendance for a student within a date range
    List<Attendance> findByStudentIdAndDateBetweenOrderByDateDesc(
            UUID studentId, LocalDate from, LocalDate to);

    // All attendance for a section on a specific date (via timetable)
    @Query("""
            SELECT a FROM Attendance a
            WHERE a.timeTable.section.id = :sectionId
            AND a.date = :date
            ORDER BY a.student.user.username ASC
            """)
    List<Attendance> findBySectionIdAndDate(
            @Param("sectionId") UUID sectionId,
            @Param("date") LocalDate date);

    // Check if attendance already marked for a student on a given timetable+date
    boolean existsByStudentIdAndTimeTableIdAndDate(
            UUID studentId, UUID timeTableId, LocalDate date);

    // Get single attendance record
    Optional<Attendance> findByStudentIdAndTimeTableIdAndDate(
            UUID studentId, UUID timeTableId, LocalDate date);

    // Count present days for a student in a date range (for % calculation)
    @Query("""
            SELECT COUNT(a) FROM Attendance a
            WHERE a.student.id = :studentId
            AND a.isPresent = true
            AND a.date BETWEEN :from AND :to
            """)
    long countPresentDays(
            @Param("studentId") UUID studentId,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to);

    // Count total marked days for a student in a date range
    long countByStudentIdAndDateBetween(UUID studentId, LocalDate from, LocalDate to);

    // All absent students for a section on a date
    @Query("""
            SELECT a FROM Attendance a
            WHERE a.timeTable.section.id = :sectionId
            AND a.date = :date
            AND a.isPresent = false
            """)
    List<Attendance> findAbsentBySectionIdAndDate(
            @Param("sectionId") UUID sectionId,
            @Param("date") LocalDate date);
}
