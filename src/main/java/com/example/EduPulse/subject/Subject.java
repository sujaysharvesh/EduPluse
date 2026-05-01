package com.example.EduPulse.subject;

import com.example.EduPulse.common.BaseEntity;
import com.example.EduPulse.standard.Standard;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "subjects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subject extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "standard_id", nullable = false)
    private Standard standard;
}
