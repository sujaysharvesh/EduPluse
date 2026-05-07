package com.example.EduPulse.attendance;

import com.example.EduPulse.common.BaseEntity;
import com.example.EduPulse.timeTable.TimeTable;
import com.example.EduPulse.user.student.Student;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "attendance",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"student_id", "time_table_id", "attendance_date"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_table_id", nullable = false)
    private TimeTable timeTable;

    @Column(name = "is_present", nullable = false)
    private Boolean isPresent;

    @Column(name = "attendance_date", nullable = false)
    private LocalDate date;
}