package com.example.EduPulse.admin;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "school_id")
    private UUID schoolId;

    @Column(name = "admin_name")
    private String adminName;

    @Column(name = "admin_password")
    private String adminPassword;

    @CreationTimestamp
    @Column(name = "create_at")
    private Instant createAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

}
