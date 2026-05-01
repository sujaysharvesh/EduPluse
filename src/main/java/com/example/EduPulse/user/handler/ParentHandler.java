package com.example.EduPulse.user.handler;

import com.example.EduPulse.exception.BadRequestException;
import com.example.EduPulse.user.User;
import com.example.EduPulse.user.dto.request.CreateUserRequest;
import com.example.EduPulse.user.mapper.ParentMapper;
import com.example.EduPulse.user.parent.Parent;
import com.example.EduPulse.user.parent.ParentRepo;
import com.example.EduPulse.user.student.Student;
import com.example.EduPulse.user.student.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ParentHandler implements UserHandler {

    private final ParentRepo parentRepo;
    private final ParentMapper parentMapper;
    private final StudentRepo studentRepo;

    @Override
    public String getRoleName() {
        return "PARENT";
    }

    @Override
    public void handle(User user, CreateUserRequest request) {

        Parent parent = Parent.builder()
                .user(user)
                .occupation(request.getOccupation())
                .alternatePhone(request.getAlternatePhone())
                .build();

        parentRepo.save(parent);
    }

    @Override
    public Object buildResponse(User user) {
        Parent parent = parentRepo.findById(user.getId()).orElseThrow(
                () -> new BadRequestException("Parent Not Found")
        );

        List<Student> kids = studentRepo.findAllByParent_Id(parent.getId());

        return parentMapper.toDto(parent, kids);
    }
}