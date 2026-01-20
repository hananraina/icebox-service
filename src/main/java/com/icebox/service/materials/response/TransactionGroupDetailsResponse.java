package com.icebox.service.materials.response;

import com.icebox.service.materials.enums.StockMovementType;
import com.icebox.service.materials.enums.TransactionGroupType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class TransactionGroupDetailsResponse {

    private UUID transactionGroupId;
    private TransactionGroupType groupType;
    private String referenceId;
    private String referenceType;
    private String remarks;
    private Instant createdAt;

    private List<TransactionLineItem> items;

    @Getter
    @AllArgsConstructor
    public static class TransactionLineItem {
        private Long transactionId;
        private Instant transactionDate;

        private Long materialId;
        private String materialName;
        private String variantName;

        private StockMovementType movementType;
        private Integer quantityDelta;

        private String sourceType;
        private String sourceId;
    }
}

