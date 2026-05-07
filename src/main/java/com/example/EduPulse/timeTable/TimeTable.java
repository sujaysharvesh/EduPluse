package com.example.EduPulse.timeTable;

import com.example.EduPulse.common.BaseEntity;
import com.example.EduPulse.section.Section;
import com.example.EduPulse.standard.Standard;
import com.example.EduPulse.subjectTeacher.SubjectTeacher;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "time_table",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"section_id", "day_of_week", "period_number"}
        )
)
public class TimeTable extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "standard_id", nullable = false)
    private Standard standard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_teacher_id", nullable = false)
    private SubjectTeacher subjectTeacher;

    @Column(name = "day_of_week", nullable = false)
    private Integer dayOfWeek;       // 1=Mon … 6=Sat

    @Column(name = "period_number", nullable = false)
    private Integer periodNumber;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    public boolean isValidTime() {
        return startTime != null
                && endTime != null
                && endTime.isAfter(startTime);
    }
}