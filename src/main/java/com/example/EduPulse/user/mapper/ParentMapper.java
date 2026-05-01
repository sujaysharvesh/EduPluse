package com.example.EduPulse.user.mapper;

import com.example.EduPulse.user.dto.response.ParentResponse;
import com.example.EduPulse.user.parent.Parent;
import com.example.EduPulse.user.student.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = StudentMapper.class)
public interface ParentMapper {

    @Mapping(target = "username", source = "parent.user.username")
    @Mapping(target = "email", source = "parent.user.email")
    @Mapping(target = "role", source = "parent.user.role")
    @Mapping(target = "phone", source = "parent.user.phone")
    @Mapping(target = "profilePic", source = "parent.user.profilePic")
    @Mapping(target = "kids", source = "students")
    ParentResponse toDto(Parent parent, List<Student> students);
}