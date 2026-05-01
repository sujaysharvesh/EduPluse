package com.example.EduPulse.user.mapper;

import com.example.EduPulse.user.dto.response.TeacherResponse;
import com.example.EduPulse.user.teacher.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    @Mapping(target = "id", expression = "java(t.getId().toString())")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "role", source = "user.role")
    @Mapping(target = "phone", source = "user.phone")
    @Mapping(target = "profilePic", source = "user.profilePic")
    @Mapping(target = "schoolId", expression = "java(t.getUser().getSchool().getId().toString())")
    @Mapping(target = "schoolName", source = "user.school.schoolName")
    @Mapping(target = "subject", source = "subject")
    @Mapping(target = "qualification", source = "qualification")
    @Mapping(target = "joiningDate", source = "joiningDate")
    TeacherResponse toDto(Teacher t);

    List<TeacherResponse> toDtoList(List<Teacher> teachers);
}
