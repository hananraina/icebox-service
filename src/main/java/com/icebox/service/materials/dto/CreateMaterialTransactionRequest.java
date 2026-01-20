package com.icebox.service.materials.dto;

import com.icebox.service.materials.enums.StockMovementType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMaterialTransactionRequest {

    @NotNull
    private Long materialId;

    private Long variantId;

    @NotNull
    private StockMovementType movementType;   // IN / OUT

    @NotNull
    private Integer quantityDelta;

    @NotNull
    private Instant transactionDate;

    private String sourceType;         // SUPPLIER / INTERNAL

    private String sourceId;           // optional

    private String remarks;
}

