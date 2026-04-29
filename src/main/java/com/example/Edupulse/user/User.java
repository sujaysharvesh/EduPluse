package com.example.Edupulse.user;


import com.example.Edupulse.common.BaseEntity;
import com.example.Edupulse.common.enums.UserRole;
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

    @Column(name = "school_id")
    private UUID schoolId;

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
    private Long phone;

    @Column(columnDefinition = "TEXT")
    private String address;
}
