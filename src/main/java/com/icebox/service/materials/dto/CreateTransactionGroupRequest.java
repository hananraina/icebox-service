package com.icebox.service.materials.dto;

import com.icebox.service.materials.enums.TransactionGroupType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTransactionGroupRequest {

//    @NotBlank
    private String tenantId;

    @NotNull
    private TransactionGroupType groupType;      // PURCHASE / PACKING / ADJUSTMENT

    private String referenceId;     // e.g. INV-123, PO-781

    private String referenceType;   // INVOICE / PACKING_ORDER / MANUAL

    private String remarks;

    @NotNull
    private UUID createdByUserId;

    @NotNull
    private List<CreateMaterialTransactionRequest> transactions;
}

