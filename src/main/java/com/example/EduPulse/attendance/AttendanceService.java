package com.example.EduPulse.attendance;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AttendanceService {

    AttendanceDto.Response mark(AttendanceDto.MarkRequest request);

    List<AttendanceDto.Response> bulkMark(AttendanceDto.BulkMarkRequest request);

    AttendanceDto.Response update(UUID id, AttendanceDto.UpdateRequest request);

    AttendanceDto.Response getById(UUID id);

    List<AttendanceDto.Response> getByStudent(UUID studentId);

    List<AttendanceDto.Response> getByStudentAndDateRange(UUID studentId, LocalDate from, LocalDate to);

    List<AttendanceDto.Response> getBySectionAndDate(UUID sectionId, LocalDate date);

    List<AttendanceDto.Response> getAbsentBySectionAndDate(UUID sectionId, LocalDate date);

    AttendanceDto.SummaryResponse getSummary(UUID studentId, LocalDate from, LocalDate to);

    void delete(UUID id);
}