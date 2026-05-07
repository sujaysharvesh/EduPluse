package com.example.EduPulse.attendance;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
interface AttendanceMapper {

    @Mapping(target = "id",           expression = "java(a.getId().toString())")
    @Mapping(target = "studentId",    expression = "java(a.getStudent().getId().toString())")
    @Mapping(target = "studentName",  source = "student.user.username")
    @Mapping(target = "rollNumber",   source = "student.rollNumber")
    @Mapping(target = "sectionId",    expression = "java(a.getTimeTable().getSection().getId().toString())")
    @Mapping(target = "sectionName",  source = "timeTable.section.name")
    @Mapping(target = "standardName", source = "timeTable.standard.name")
    @Mapping(target = "timeTableId",  expression = "java(a.getTimeTable().getId().toString())")
    @Mapping(target = "subjectName",  source = "timeTable.subjectTeacher.subject.name")
    @Mapping(target = "periodNumber", source = "timeTable.periodNumber")
    @Mapping(target = "dayOfWeek",    source = "timeTable.dayOfWeek")
    @Mapping(target = "isPresent",    source = "isPresent")
    @Mapping(target = "date",         source = "date")
    AttendanceDto.Response toDto(Attendance a);

    List<AttendanceDto.Response> toDtoList(List<Attendance> list);
}
