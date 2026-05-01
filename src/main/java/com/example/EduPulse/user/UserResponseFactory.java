package com.example.EduPulse.user;

import com.example.EduPulse.user.mapper.ParentMapper;
import com.example.EduPulse.user.mapper.StudentMapper;
import com.example.EduPulse.user.mapper.TeacherMapper;
import com.example.EduPulse.user.parent.Parent;
import com.example.EduPulse.user.parent.ParentRepo;
import com.example.EduPulse.user.student.Student;
import com.example.EduPulse.user.student.StudentRepo;
import com.example.EduPulse.user.teacher.Teacher;
import com.example.EduPulse.user.teacher.TeacherRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class UserResponseFactory {

    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;
    private final ParentMapper parentMapper;
    private final StudentRepo studentRepo;
    private final TeacherRepo teacherRepo;
    private final ParentRepo parentRepo;

    public Object build(User user) {

        return switch (user.getRole().getName()) {

            case "STUDENT" -> {
                Student s = studentRepo.findById(user.getId())
                        .orElseThrow(() -> new RuntimeException("Student not found"));
                yield studentMapper.toDto(s);
            }

            case "TEACHER" -> {
                Teacher t = teacherRepo.findById(user.getId())
                        .orElseThrow(() -> new RuntimeException("Teacher not found"));
                yield teacherMapper.toDto(t);
            }

            case "PARENT" -> {
                Parent p = parentRepo.findById(user.getId())
                        .orElseThrow(() -> new RuntimeException("Parent not found"));
                List<Student> kids = studentRepo.findAllByParent_Id(user.getId());
                yield parentMapper.toDto(p, kids);
            }

            default -> throw new RuntimeException("Unsupported role");
        };
    }
}
