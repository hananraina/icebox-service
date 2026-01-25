package com.icebox.service.financialyear.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "financial_year")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinancialYearEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true, length = 20)
    private String code;          // FY_2026_27

    @Column(name = "display_name", nullable = false, length = 10)
    private String displayName;   // 2026-27

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "is_locked", nullable = false)
    private boolean isLocked;

    private Instant lockedAt;
    private UUID lockedBy;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private UUID createdBy;

    private Instant updatedAt;
    private UUID updatedBy;

    private String tenantId;
}

