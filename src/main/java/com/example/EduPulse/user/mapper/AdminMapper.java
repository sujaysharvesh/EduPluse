package com.example.EduPulse.user.mapper;

import com.example.EduPulse.user.User;
import com.example.EduPulse.user.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "profilePic", source = "profilePic")
    @Mapping(target = "schoolName", source = "user.school.schoolName")
    UserResponse toDto(User user);


}
