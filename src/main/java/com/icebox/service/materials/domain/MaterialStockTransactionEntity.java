package com.icebox.service.materials.domain;

import com.icebox.service.materials.enums.StockMovementType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "material_stock_transactions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialStockTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tenantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private MaterialEntity material;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id")
    private MaterialVariantEntity variant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StockMovementType movementType;

    @Column(nullable = false)
    private Integer quantityDelta;

    @Column(nullable = false)
    private Instant transactionDate;

    private String invoiceNumber;
    private String vendorName;
    private BigDecimal purchaseRate;
    private String remarks;

    @Column(nullable = false)
    private UUID createdByUserId;

    private Instant createdAt;

    // TODO add  @ManyToOne(fetch = LAZY) if you want joins
    @Column(name = "transaction_group_id", nullable = false)
    private UUID transactionGroupId;

    @Column(name = "source_type", length = 30)
    private String sourceType;   // SUPPLIER / INTERNAL / WAREHOUSE / CLIENT

    @Column(name = "source_id", length = 100)
    private String sourceId;     // nullable

}

