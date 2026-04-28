package com.example.Edupulse.user;


import com.example.Edupulse.common.BaseEntity;
import com.example.Edupulse.common.enums.UserRole;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity {


    @Column(nullable = false, unique = false, length = 100)
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
    private Integer phone;

    @Column(columnDefinition = "TEXT")
    private String address;


}
