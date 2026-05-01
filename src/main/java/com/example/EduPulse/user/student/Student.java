package com.example.EduPulse.user.student;

import com.example.EduPulse.section.Section;
import com.example.EduPulse.standard.Standard;
import com.example.EduPulse.user.User;
import com.example.EduPulse.user.parent.Parent;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {  // ← no extends

    @Id
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "standard_id")
    private Standard standard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private Section section;

    @Column(name = "roll_number")
    private String rollNumber;

    @Column(name = "blood_group")
    private String bloodGroup;

    @CreationTimestamp
    @Column(name = "admission_date")
    private LocalDate admissionDate;
}
