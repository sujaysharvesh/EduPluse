package com.example.EduPulse.timeTable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TimeTableRepo extends JpaRepository<TimeTable, UUID> {

    // All slots for a specific section
    List<TimeTable> findBySectionIdOrderByDayOfWeekAscPeriodNumberAsc(UUID sectionId);

    // All slots for a specific section on a specific day
    List<TimeTable> findBySectionIdAndDayOfWeekOrderByPeriodNumberAsc(UUID sectionId, Integer dayOfWeek);

    // All slots assigned to a specific teacher (via SubjectTeacher)
    @Query("""
            SELECT t FROM TimeTable t
            WHERE t.subjectTeacher.teacher.id = :teacherId
            ORDER BY t.dayOfWeek ASC, t.periodNumber ASC
            """)
    List<TimeTable> findByTeacherId(@Param("teacherId") UUID teacherId);

    // All slots for a specific standard
    List<TimeTable> findByStandardIdOrderByDayOfWeekAscPeriodNumberAsc(UUID standardId);

    // Period-1 slot for a section on a given day (used for attendance)
    Optional<TimeTable> findBySectionIdAndDayOfWeekAndPeriodNumber(
            UUID sectionId, Integer dayOfWeek, Integer periodNumber);

    // Check if a slot already exists (for conflict detection)
    boolean existsBySectionIdAndDayOfWeekAndPeriodNumber(
            UUID sectionId, Integer dayOfWeek, Integer periodNumber);

    // Check conflict excluding a specific timetable entry (used during update)
    boolean existsBySectionIdAndDayOfWeekAndPeriodNumberAndIdNot(
            UUID sectionId, Integer dayOfWeek, Integer periodNumber, UUID id);

    // All slots by subject-teacher mapping
    List<TimeTable> findBySubjectTeacherId(UUID subjectTeacherId);

}
