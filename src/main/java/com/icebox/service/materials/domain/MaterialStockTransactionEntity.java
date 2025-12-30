package com.icebox.service.materials.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "material_stock_transactions")
@Getter @Setter
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

    @Column(nullable = false)
    private String transactionType;

    @Column(nullable = false)
    private Integer quantityChange;

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    private String invoiceNumber;
    private String vendorName;
    private BigDecimal purchaseRate;
    private String remarks;

    @Column(nullable = false)
    private UUID createdByUserId;

    private LocalDateTime createdAt;
}

