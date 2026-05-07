package com.example.EduPulse.exam;

import com.example.EduPulse.common.BaseEntity;
import com.example.EduPulse.exam.examType.ExamType;
import com.example.EduPulse.section.Section;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "exams",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"exam_type_id", "section_id", "academic_year"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exam extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_type_id", nullable = false)
    private ExamType examType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    @Column(name = "academic_year", nullable = false)
    private String academicYear;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;
}