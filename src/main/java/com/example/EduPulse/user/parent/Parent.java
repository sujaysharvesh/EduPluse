package com.example.EduPulse.user.parent;

import com.example.EduPulse.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "parents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Parent {  // ← no extends

    @Id
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "alternate_phone")
    private String alternatePhone;
}
