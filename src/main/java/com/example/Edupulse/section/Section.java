package com.example.Edupulse.section;

import com.example.Edupulse.common.BaseEntity;
import com.example.Edupulse.standard.Standard;
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
@Table(name = "sections")
public class Section extends BaseEntity {


    @Column(name = "name", nullable = false, length = 10)
    private String name; // e.g. "A", "B", "C"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "standard_id", nullable = false)
    private Standard standard;

}