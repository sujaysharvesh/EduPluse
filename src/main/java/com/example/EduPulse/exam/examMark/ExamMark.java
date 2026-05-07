package com.example.EduPulse.exam.examMark;

import com.example.EduPulse.common.BaseEntity;
import com.example.EduPulse.exam.Exam;
import com.example.EduPulse.subject.Subject;
import com.example.EduPulse.user.student.Student;
import com.example.EduPulse.user.teacher.Teacher;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "exam_marks",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "student_id",
                                "exam_id",
                                "subject_id"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamMark extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(name = "obtained_mark")
    private Integer obtainedMark;

    @Column(name = "grade")
    private String grade;

    @Column(name = "is_absent")
    private Boolean isAbsent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entered_by")
    private Teacher enteredBy;
}