package com.example.EduPulse.user.mapper;

import com.example.EduPulse.user.dto.response.StudentResponse;
import com.example.EduPulse.user.student.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "id", expression = "java(s.getId().toString())")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "role", source = "user.role")
    @Mapping(target = "phone", source = "user.phone")
    @Mapping(target = "profilePic", source = "user.profilePic")
    @Mapping(target = "schoolName", source = "user.school.schoolName")
    @Mapping(target = "standardName", source = "standard.name")
    @Mapping(target = "sectionName", source = "section.name")
    StudentResponse toDto(Student s);

    List<StudentResponse> toDtoList(List<Student> students);
}