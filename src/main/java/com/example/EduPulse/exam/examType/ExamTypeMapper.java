package com.example.EduPulse.exam.examType;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExamTypeMapper {

    @Mapping(target = "id", expression = "java(e.getId().toString())")
    @Mapping(target = "examName", source = "examName")
    @Mapping(target = "maxMark",  source = "maxMark")
    ExamTypeDto.Response toDto(ExamType e);

    List<ExamTypeDto.Response> toDtoList(List<ExamType> list);
}
