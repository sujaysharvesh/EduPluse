package com.example.EduPulse.timeTable;

import java.util.List;
import java.util.UUID;

public interface TimeTableService {

    TimeTableDto.Response create(TimeTableDto.CreateRequest request);

    TimeTableDto.Response update(UUID id, TimeTableDto.UpdateRequest request);

    void delete(UUID id);

    TimeTableDto.Response getById(UUID id);

    List<TimeTableDto.Response> getBySection(UUID sectionId);

    List<TimeTableDto.Response> getBySectionAndDay(UUID sectionId, Integer dayOfWeek);

    List<TimeTableDto.Response> getByTeacher(UUID teacherId);

    List<TimeTableDto.Response> getByStandard(UUID standardId);

    // Returns the period-1 teacher for attendance purposes
    TimeTableDto.Response getFirstPeriod(UUID sectionId, Integer dayOfWeek);

}
