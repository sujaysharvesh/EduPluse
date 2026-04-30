package com.example.Edupulse.school;

import com.example.Edupulse.common.BaseEntity;
import com.example.Edupulse.common.enums.SchoolStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "schools")
public class School extends BaseEntity {

    @Column(name = "branch_name", nullable = false, length = 100)
    private String schoolName;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(length = 50)
    private String city;

    @Column(length = 50)
    private String state;

    @Column(length = 10)
    private String pincode;

    @Column(length = 20)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private SchoolStatus status;
}