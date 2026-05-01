package com.example.EduPulse.subjectTeacher;


import com.example.EduPulse.exception.BadRequestException;
import com.example.EduPulse.exception.ResourceNotFoundException;
import com.example.EduPulse.subject.Subject;
import com.example.EduPulse.subject.SubjectRepo;
import com.example.EduPulse.subjectTeacher.dto.request.SubjectTeacherRequest;
import com.example.EduPulse.subjectTeacher.dto.response.TeacherSubjectsResponse;
import com.example.EduPulse.user.teacher.Teacher;
import com.example.EduPulse.user.teacher.TeacherRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubjectTeacherServiceImp implements SubjectTeacherService {

    private final SubjectTeacherRepo subjectTeacherRepo;
    private final TeacherRepo teacherRepo;
    private final SubjectRepo subjectRepo;


    @Override
    public void assign(SubjectTeacherRequest subjectTeacherRequest) {

        if(subjectTeacherRepo.existsBySubject_Id(subjectTeacherRequest.getSubjectId())) {
            throw new BadRequestException("Teacher already assigned to this subject");
        }

        Teacher teacher = teacherRepo.findById(subjectTeacherRequest.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));

        Subject subject = subjectRepo.findById(subjectTeacherRequest.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

        SubjectTeacher st = SubjectTeacher.builder()
                .teacher(teacher)
                .subject(subject)
                .build();

        subjectTeacherRepo.save(st);

    }

    @Override
    public List<TeacherSubjectsResponse> teacherSubjects(String teacherId) {

        UUID id = UUID.fromString(teacherId);

        teacherRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Teacher Not Found")
        );

        List<SubjectTeacher> st = subjectTeacherRepo.findAllWithDetailsByTeacherId(id);

        return st.stream()
                .map(s -> TeacherSubjectsResponse.builder()
                        .subjectId(s.getSubject().getId())
                        .subjectName(s.getSubject().getName())
                        .standardId(s.getSubject().getStandard().getId())
                        .standard(s.getSubject().getStandard().getName())
                        .build()
                )
                .toList();
    }

    @Override
    public void removeTeacherSubject(String teacherId, String subjectId) {

        UUID tId = UUID.fromString(teacherId);
        UUID sId = UUID.fromString(subjectId);

        if (!subjectTeacherRepo.existsByTeacher_IdAndSubject_Id(tId, sId)) {
            throw new ResourceNotFoundException("Teacher-Subject mapping not found");
        }

        subjectTeacherRepo.deleteByTeacher_IdAndSubject_Id(tId, sId);
    }


}
