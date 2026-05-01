package com.example.EduPulse.standard;

import com.example.EduPulse.common.BaseEntity;
import com.example.EduPulse.school.School;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "standards")
public class Standard extends BaseEntity {

    @Column(name = "name", nullable = false, length = 50)
    private String name; // e.g. "Standard 1", "Standard 10"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

}