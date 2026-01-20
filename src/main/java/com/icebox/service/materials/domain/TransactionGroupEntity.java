package com.icebox.service.materials.domain;

import com.icebox.service.materials.enums.TransactionGroupType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "material_transaction_groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionGroupEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false, length = 100)
    private String tenantId;

    @Enumerated(EnumType.STRING)
    @Column(name = "group_type", nullable = false, length = 30)
    private TransactionGroupType groupType;

    @Column(name = "reference_id", length = 100)
    private String referenceId;

    @Column(name = "reference_type", length = 30)
    private String referenceType;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_by_user_id", nullable = false)
    private UUID createdByUserId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
}

