package com.example.EduPulse.timeTable;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TimeTableMapper {

    @Mapping(target = "id",               expression = "java(t.getId().toString())")
    @Mapping(target = "standardId",       expression = "java(t.getStandard().getId().toString())")
    @Mapping(target = "standardName",     source = "standard.name")
    @Mapping(target = "sectionId",        expression = "java(t.getSection().getId().toString())")
    @Mapping(target = "sectionName",      source = "section.name")
    @Mapping(target = "academicYear",     source = "section.academicYear")
    @Mapping(target = "subjectTeacherId", expression = "java(t.getSubjectTeacher().getId().toString())")
    @Mapping(target = "teacherId",        expression = "java(t.getSubjectTeacher().getTeacher().getId().toString())")
    @Mapping(target = "teacherName",      source = "subjectTeacher.teacher.user.username")
    @Mapping(target = "subjectId",        expression = "java(t.getSubjectTeacher().getSubject().getId().toString())")
    @Mapping(target = "subjectName",      source = "subjectTeacher.subject.name")
    @Mapping(target = "dayOfWeek",        source = "dayOfWeek")
    @Mapping(target = "periodNumber",     source = "periodNumber")
    @Mapping(target = "startTime",        source = "startTime")
    @Mapping(target = "endTime",          source = "endTime")

    TimeTableDto.Response toDto(TimeTable t);

    List<TimeTableDto.Response> toDtoList(List<TimeTable> timeTables);
}
