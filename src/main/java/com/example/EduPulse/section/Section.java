package com.example.EduPulse.section;

import com.example.EduPulse.common.BaseEntity;
import com.example.EduPulse.standard.Standard;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sections")
public class Section extends BaseEntity {


    @Column(name = "name", nullable = false, length = 10)
    private String name; // e.g. "A", "B", "C"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "standard_id", nullable = false)
    private Standard standard;

}