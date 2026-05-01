package com.example.EduPulse.user.handler;

import com.example.EduPulse.exception.BadRequestException;
import com.example.EduPulse.exception.ResourceNotFoundException;
import com.example.EduPulse.section.SectionRepo;
import com.example.EduPulse.standard.StandardRepo;
import com.example.EduPulse.user.User;
import com.example.EduPulse.user.dto.request.CreateUserRequest;
import com.example.EduPulse.user.mapper.StudentMapper;
import com.example.EduPulse.user.parent.Parent;
import com.example.EduPulse.user.parent.ParentRepo;
import com.example.EduPulse.user.student.Student;
import com.example.EduPulse.user.student.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentHandler implements UserHandler {

    private final StudentRepo studentRepo;
    private final StandardRepo standardRepo;
    private final SectionRepo sectionRepo;
    private final ParentRepo parentRepo;
    private final StudentMapper studentMapper;

    @Override
    public String getRoleName() {
        return "STUDENT";
    }

    @Override
    public void handle(User user, CreateUserRequest request) {

        if (request.getRollNumber() == null) {
            throw new BadRequestException("Roll number is required for student");
        }

        Student student = Student.builder()
                .user(user)
                .rollNumber(request.getRollNumber())
                .admissionDate(request.getAdmissionDate())
                .build();

        if (request.getStandardId() != null) {
            student.setStandard(
                    standardRepo.findById(request.getStandardId())
                            .orElseThrow(() -> new ResourceNotFoundException("Standard not found"))
            );
        }

        if (request.getSectionId() != null) {
            student.setSection(
                    sectionRepo.findById(request.getSectionId())
                            .orElseThrow(() -> new ResourceNotFoundException("Section not found"))
            );
        }

        if (request.getParentId() != null) {
            Parent parent = parentRepo.findById(request.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent not found"));
            student.setParent(parent);
        }

        studentRepo.save(student);
    }

    @Override
    public Object buildResponse(User user) {

        Student student = studentRepo.findById(user.getId()).orElseThrow(
                () -> new BadRequestException("Student Not Found")
        );

        return studentMapper.toDto(student);

    }
}
