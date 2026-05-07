package com.example.EduPulse.exam.examType;

import com.example.EduPulse.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "exam_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamType extends BaseEntity {

    @Column(name = "exam_name", nullable = false, unique = true)
    private String examName;

    @Column(name = "max_mark", nullable = false)
    private Integer maxMark;
}