package com.example.EduPulse.exam.examMark;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExamMarkMapper {

    @Mapping(target = "id",           expression = "java(em.getId().toString())")
    @Mapping(target = "studentId",    expression = "java(em.getStudent().getId().toString())")
    @Mapping(target = "studentName",  source = "student.user.username")
    @Mapping(target = "rollNumber",   source = "student.rollNumber")
    @Mapping(target = "examId",       expression = "java(em.getExam().getId().toString())")
    @Mapping(target = "examTypeName", source = "exam.examType.examName")
    @Mapping(target = "academicYear", source = "exam.academicYear")
    @Mapping(target = "maxMark",      source = "exam.examType.maxMark")
    @Mapping(target = "subjectId",    expression = "java(em.getSubject().getId().toString())")
    @Mapping(target = "subjectName",  source = "subject.name")
    @Mapping(target = "obtainedMark", source = "obtainedMark")
    @Mapping(target = "grade",        source = "grade")
    @Mapping(target = "isAbsent",     source = "isAbsent")
    @Mapping(target = "percentage",   expression = "java(calcPercentage(em))")
    @Mapping(target = "enteredBy",    expression = "java(em.getEnteredBy() != null ? em.getEnteredBy().getId().toString() : null)")
    ExamMarkDto.Response toDto(ExamMark em);

    List<ExamMarkDto.Response> toDtoList(List<ExamMark> list);

    default Double calcPercentage(ExamMark em) {
        if (em.getIsAbsent() == Boolean.TRUE || em.getObtainedMark() == null) return null;
        int max = em.getExam().getExamType().getMaxMark();
        if (max == 0) return null;
        return Math.round((em.getObtainedMark() * 100.0 / max) * 100.0) / 100.0;
    }
}
