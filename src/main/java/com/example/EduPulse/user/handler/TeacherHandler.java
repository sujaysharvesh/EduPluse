package com.example.EduPulse.user.handler;

import com.example.EduPulse.exception.BadRequestException;
import com.example.EduPulse.user.User;
import com.example.EduPulse.user.dto.request.CreateUserRequest;
import com.example.EduPulse.user.mapper.TeacherMapper;
import com.example.EduPulse.user.teacher.Teacher;
import com.example.EduPulse.user.teacher.TeacherRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeacherHandler implements UserHandler {

    private final TeacherRepo teacherRepo;
    private final TeacherMapper teacherMapper;

    @Override
    public String getRoleName() {
        return "TEACHER";
    }

    @Override
    public void handle(User user, CreateUserRequest request) {

        Teacher teacher = Teacher.builder()
                .user(user)
                .qualification(request.getQualification())
                .subject(request.getSubject())
                .joiningDate(request.getJoiningDate())
                .build();

        teacherRepo.save(teacher);
    }

    @Override
    public Object buildResponse(User user) {
        Teacher teacher = teacherRepo.findById(user.getId()).orElseThrow(
                () -> new BadRequestException("Teacher Not Found")
        );

        return teacherMapper.toDto(teacher);
    }
}
