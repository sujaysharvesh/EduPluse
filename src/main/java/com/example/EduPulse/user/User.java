package com.example.EduPulse.user;


import com.example.EduPulse.common.BaseEntity;
import com.example.EduPulse.school.School;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private UserRole role;

    @Column(name = "profile_pic")
    private String profilePic;

    @Column(length = 20)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String address;
}
