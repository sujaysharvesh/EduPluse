package com.example.EduPulse.subjectTeacher;


import com.example.EduPulse.subjectTeacher.dto.request.SubjectTeacherRequest;
import com.example.EduPulse.subjectTeacher.dto.response.TeacherSubjectsResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubjectTeacherService {

    void assign(SubjectTeacherRequest subjectTeacherRequest);

    List<TeacherSubjectsResponse> teacherSubjects(String teacherId);

    void removeTeacherSubject(String teacherId, String subjectId);

}
