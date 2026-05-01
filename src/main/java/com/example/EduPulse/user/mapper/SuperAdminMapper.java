package com.example.EduPulse.user.mapper;


import com.example.EduPulse.user.User;
import com.example.EduPulse.user.dto.response.ParentResponse;
import com.example.EduPulse.user.dto.response.UserResponse;
import com.example.EduPulse.user.parent.Parent;
import com.example.EduPulse.user.student.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SuperAdminMapper {

    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "profilePic", source = "profilePic")
    UserResponse toDto(User user);

}