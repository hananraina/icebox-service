package com.icebox.service.materials.controller;

import com.icebox.service.materials.dto.CreateTransactionGroupRequest;
import com.icebox.service.materials.enums.TransactionGroupType;
import com.icebox.service.materials.response.TransactionGroupDetailsResponse;
import com.icebox.service.materials.response.TransactionListItem;
import com.icebox.service.materials.service.MaterialTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/materials")
@CrossOrigin(origins = "*")
@RestController
public class MaterialTransactionController {
    private final MaterialTransactionService materialTransactionService;

    @GetMapping("/transactions")
    public Page<TransactionListItem> listTransactions(
            @RequestHeader("X-FusionAuth-TenantId") String tenantId,
            @RequestParam(required = false) Long materialId,
            @RequestParam(required = false) TransactionGroupType groupType,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            Instant from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            Instant to,
            Pageable pageable
    ) {
        return materialTransactionService.listTransactions(
                tenantId, materialId, groupType, from, to, pageable
        );
    }

    @PostMapping("/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public UUID createTransaction(
            @RequestBody @Valid CreateTransactionGroupRequest request,
            @RequestHeader("X-FusionAuth-TenantId") String tenantId
    ) {
        request.setTenantId(tenantId);
        return materialTransactionService.createTransactionGroup(request);
    }

    @GetMapping("/transactions/{groupId}")
    public TransactionGroupDetailsResponse getTransactionDetails(
            @PathVariable UUID groupId
    ) {
        return materialTransactionService.getTransactionGroupDetails(groupId);
    }

}
