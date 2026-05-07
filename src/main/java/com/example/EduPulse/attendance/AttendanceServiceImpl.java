package com.example.EduPulse.attendance;

import com.example.EduPulse.exception.AttendanceException;
import com.example.EduPulse.timeTable.TimeTable;
import com.example.EduPulse.timeTable.TimeTableRepo;
import com.example.EduPulse.user.student.Student;
import com.example.EduPulse.user.student.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepo attendanceRepository;
    private final TimeTableRepo timeTableRepository;
    private final StudentRepo studentRepository;
    private final AttendanceMapper     mapper;

    @Override
    @Transactional
    public AttendanceDto.Response mark(AttendanceDto.MarkRequest req) {
        validateNotFuture(req.date());

        if (attendanceRepository.existsByStudentIdAndTimeTableIdAndDate(
                req.studentId(), req.timeTableId(), req.date())) {
            throw new AttendanceException.AlreadyMarked(req.studentId().toString(), req.date());
        }

        Student   student   = findStudentOrThrow(req.studentId());
        TimeTable timeTable = findTimeTableOrThrow(req.timeTableId());

        Attendance attendance = Attendance.builder()
                .student(student)
                .timeTable(timeTable)
                .isPresent(req.isPresent())
                .date(req.date())
                .build();

        return mapper.toDto(attendanceRepository.save(attendance));
    }

    @Override
    @Transactional
    public List<AttendanceDto.Response> bulkMark(AttendanceDto.BulkMarkRequest req) {
        validateNotFuture(req.date());

        TimeTable timeTable = findTimeTableOrThrow(req.timeTableId());

        List<Attendance> records = req.entries().stream()
                .map(entry -> {
                    if (attendanceRepository.existsByStudentIdAndTimeTableIdAndDate(
                            entry.studentId(), req.timeTableId(), req.date())) {
                        throw new AttendanceException.AlreadyMarked(
                                entry.studentId().toString(), req.date());
                    }
                    Student student = findStudentOrThrow(entry.studentId());
                    return Attendance.builder()
                            .student(student)
                            .timeTable(timeTable)
                            .isPresent(entry.isPresent())
                            .date(req.date())
                            .build();
                })
                .toList();

        return mapper.toDtoList(attendanceRepository.saveAll(records));
    }

    @Override
    @Transactional
    public AttendanceDto.Response update(UUID id, AttendanceDto.UpdateRequest req) {
        Attendance attendance = findOrThrow(id);
        attendance.setIsPresent(req.isPresent());
        return mapper.toDto(attendanceRepository.save(attendance));
    }

    @Override
    @Transactional(readOnly = true)
    public AttendanceDto.Response getById(UUID id) {
        return mapper.toDto(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDto.Response> getByStudent(UUID studentId) {
        return mapper.toDtoList(
                attendanceRepository.findByStudentIdOrderByDateDesc(studentId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDto.Response> getByStudentAndDateRange(
            UUID studentId, LocalDate from, LocalDate to) {
        return mapper.toDtoList(
                attendanceRepository.findByStudentIdAndDateBetweenOrderByDateDesc(
                        studentId, from, to));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDto.Response> getBySectionAndDate(UUID sectionId, LocalDate date) {
        return mapper.toDtoList(
                attendanceRepository.findBySectionIdAndDate(sectionId, date));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDto.Response> getAbsentBySectionAndDate(UUID sectionId, LocalDate date) {
        return mapper.toDtoList(
                attendanceRepository.findAbsentBySectionIdAndDate(sectionId, date));
    }

    @Override
    @Transactional(readOnly = true)
    public AttendanceDto.SummaryResponse getSummary(UUID studentId, LocalDate from, LocalDate to) {
        Student student   = findStudentOrThrow(studentId);
        long total        = attendanceRepository.countByStudentIdAndDateBetween(studentId, from, to);
        long present      = attendanceRepository.countPresentDays(studentId, from, to);
        long absent       = total - present;
        double percentage = total == 0 ? 0.0 : Math.round((present * 100.0 / total) * 100.0) / 100.0;

        return new AttendanceDto.SummaryResponse(
                studentId.toString(),
                student.getUser().getUsername(),
                from,
                to,
                total,
                present,
                absent,
                percentage
        );
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        attendanceRepository.delete(findOrThrow(id));
    }

    // ── Helpers ───────────────────────────────

    private Attendance findOrThrow(UUID id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new AttendanceException.NotFound(id.toString()));
    }

    private Student findStudentOrThrow(UUID id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found: " + id));
    }

    private TimeTable findTimeTableOrThrow(UUID id) {
        return timeTableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Timetable not found: " + id));
    }

    private void validateNotFuture(LocalDate date) {
        if (date.isAfter(LocalDate.now())) {
            throw new AttendanceException.FutureDateNotAllowed();
        }
    }
}
