package com.icebox.service.materials.response;

import com.icebox.service.materials.enums.StockMovementType;
import com.icebox.service.materials.enums.TransactionGroupType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class TransactionListItem {

    private Long transactionId;
    private Instant transactionDate;

    private Long materialId;
    private String materialName;
    private String variantName;

    private StockMovementType movementType;
    private Integer quantityDelta;

    private TransactionGroupType groupType;
    private String referenceId;

    private String createdBy;

    private String sourceType;
    private String sourceId;
}

