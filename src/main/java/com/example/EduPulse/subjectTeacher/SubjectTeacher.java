package com.example.EduPulse.subjectTeacher;

import com.example.EduPulse.subject.Subject;
import com.example.EduPulse.user.teacher.Teacher;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Entity
@Table(name = "subject_teacher")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectTeacher {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;


}
