package com.example.EduPulse.exam;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExamMapper {

    @Mapping(target = "id",           expression = "java(e.getId().toString())")
    @Mapping(target = "examTypeId",   expression = "java(e.getExamType().getId().toString())")
    @Mapping(target = "examTypeName", source = "examType.examName")
    @Mapping(target = "maxMark",      source = "examType.maxMark")
    @Mapping(target = "sectionId",    expression = "java(e.getSection().getId().toString())")
    @Mapping(target = "sectionName",  source = "section.name")
    @Mapping(target = "standardName", source = "section.standard.name")
    @Mapping(target = "academicYear", source = "academicYear")
    @Mapping(target = "startDate",    source = "startDate")
    @Mapping(target = "endDate",      source = "endDate")
    ExamDto.Response toDto(Exam e);

    List<ExamDto.Response> toDtoList(List<Exam> list);
}
