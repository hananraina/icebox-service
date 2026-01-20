package com.icebox.service.materials.dto;

import com.icebox.service.materials.enums.TransactionGroupType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StockTransactionDTO {
    private Long materialId;
    private Long variantId;
    private TransactionGroupType transactionGroupType;
    private Integer quantityChange;
    private LocalDateTime transactionDate;
    private String remarks;
}

