package com.example.Edupulse.user;


import com.example.Edupulse.common.BaseEntity;
import com.example.Edupulse.common.enums.UserRole;
import com.example.Edupulse.school.School;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private UserRole role;

    @Column(name = "profile_pic")
    private String profilePic;

    @Column(length = 20)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String address;
}
