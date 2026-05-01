package com.example.EduPulse.user.teacher;

import com.example.EduPulse.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "teachers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {  // ← no extends

    @Id
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "qualification")
    private String qualification;

    @Column(name = "subject")
    private String subject;

    @Column(name = "joining_date")
    private LocalDate joiningDate;
}
