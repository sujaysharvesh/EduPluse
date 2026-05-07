package com.example.EduPulse.timeTable;


import com.example.EduPulse.exception.TimeTableException;
import com.example.EduPulse.section.Section;
import com.example.EduPulse.section.SectionRepo;
import com.example.EduPulse.standard.Standard;
import com.example.EduPulse.standard.StandardRepo;

import com.example.EduPulse.subjectTeacher.SubjectTeacher;
import com.example.EduPulse.subjectTeacher.SubjectTeacherRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TimeTableServiceImpl implements TimeTableService {

    private final TimeTableRepo timeTableRepo;
    private final SectionRepo sectionRepo;
    private final StandardRepo standardRepo;
    private final SubjectTeacherRepo subjectTeacherRepo;
    private final TimeTableMapper timeTableMapper;

    @Override
    @Transactional
    public TimeTableDto.Response create(TimeTableDto.CreateRequest req) {
        if (!req.endTime().isAfter(req.startTime())) {
            throw new TimeTableException.InvalidTime();
        }

        if (timeTableRepo.existsBySectionIdAndDayOfWeekAndPeriodNumber(
                req.sectionId(), req.dayOfWeek(), req.periodNumber())) {
            throw new TimeTableException.SlotConflict(
                    req.sectionId().toString(), req.dayOfWeek(), req.periodNumber());
        }

        Standard standard = standardRepo.findById(req.standardId())
                .orElseThrow(() -> new RuntimeException("Standard not found: " + req.standardId()));

        Section section = sectionRepo.findById(req.sectionId())
                .orElseThrow(() -> new RuntimeException("Section not found: " + req.sectionId()));

        SubjectTeacher subjectTeacher = subjectTeacherRepo.findById(req.subjectTeacherId())
                .orElseThrow(() -> new RuntimeException("SubjectTeacher not found: " + req.subjectTeacherId()));

        TimeTable timeTable = TimeTable.builder()
                .standard(standard)
                .section(section)
                .subjectTeacher(subjectTeacher)
                .dayOfWeek(req.dayOfWeek())
                .periodNumber(req.periodNumber())
                .startTime(req.startTime())
                .endTime(req.endTime())
                .build();

        return timeTableMapper.toDto(timeTable);
    }



    @Override
    @Transactional
    public TimeTableDto.Response update(UUID id, TimeTableDto.UpdateRequest req) {

        TimeTable timeTable = findOrThrow(id);

        var newStart = req.startTime() != null ? req.startTime() : timeTable.getStartTime();
        var newEnd   = req.endTime()   != null ? req.endTime()   : timeTable.getEndTime();
        if (!newEnd.isAfter(newStart)) {
            throw new TimeTableException.InvalidTime();
        }

        Integer newDay    = req.dayOfWeek()    != null ? req.dayOfWeek()    : timeTable.getDayOfWeek();
        Integer newPeriod = req.periodNumber() != null ? req.periodNumber() : timeTable.getPeriodNumber();

        boolean dayOrPeriodChanged = !newDay.equals(timeTable.getDayOfWeek())
                || !newPeriod.equals(timeTable.getPeriodNumber());

        if (dayOrPeriodChanged && timeTableRepo
                .existsBySectionIdAndDayOfWeekAndPeriodNumberAndIdNot(
                        timeTable.getSection().getId(), newDay, newPeriod, id)) {
            throw new TimeTableException.SlotConflict(
                    timeTable.getSection().getId().toString(), newDay, newPeriod);
        }

        // Apply updates
        if (req.subjectTeacherId() != null) {
            SubjectTeacher st = subjectTeacherRepo.findById(req.subjectTeacherId())
                    .orElseThrow(() -> new RuntimeException(
                            "SubjectTeacher not found: " + req.subjectTeacherId()));
            timeTable.setSubjectTeacher(st);
        }

        if (req.dayOfWeek()    != null) timeTable.setDayOfWeek(req.dayOfWeek());
        if (req.periodNumber() != null) timeTable.setPeriodNumber(req.periodNumber());
        if (req.startTime()    != null) timeTable.setStartTime(req.startTime());
        if (req.endTime()      != null) timeTable.setEndTime(req.endTime());

        return timeTableMapper.toDto(timeTableRepo.save(timeTable));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        TimeTable timeTable = findOrThrow(id);
        timeTableRepo.delete(timeTable);
    }

    @Override
    @Transactional(readOnly = true)
    public TimeTableDto.Response getById(UUID id) {
        return timeTableMapper.toDto(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimeTableDto.Response> getBySection(UUID sectionId) {
        return timeTableMapper.toDtoList(
                timeTableRepo.findBySectionIdOrderByDayOfWeekAscPeriodNumberAsc(sectionId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimeTableDto.Response> getBySectionAndDay(UUID sectionId, Integer dayOfWeek) {
        return timeTableMapper.toDtoList(
                timeTableRepo.findBySectionIdAndDayOfWeekOrderByPeriodNumberAsc(
                        sectionId, dayOfWeek));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimeTableDto.Response> getByTeacher(UUID teacherId) {
        return timeTableMapper.toDtoList(
                timeTableRepo.findByTeacherId(teacherId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimeTableDto.Response> getByStandard(UUID standardId) {
        return timeTableMapper.toDtoList(
                timeTableRepo.findByStandardIdOrderByDayOfWeekAscPeriodNumberAsc(standardId));
    }

    @Override
    @Transactional(readOnly = true)
    public TimeTableDto.Response getFirstPeriod(UUID sectionId, Integer dayOfWeek) {
        return timeTableMapper.toDto(
                timeTableRepo.findBySectionIdAndDayOfWeekAndPeriodNumber(
                                sectionId, dayOfWeek, 1)
                        .orElseThrow(() -> new RuntimeException(
                                "No period-1 slot found for section " + sectionId
                                        + " on day " + dayOfWeek)));
    }

    private TimeTable findOrThrow(UUID id) {
        return timeTableRepo.findById(id)
                .orElseThrow(() -> new TimeTableException.NotFound(id.toString()));
    }
}
