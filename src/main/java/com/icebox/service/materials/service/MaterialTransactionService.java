package com.icebox.service.materials.service;

import com.icebox.service.materials.domain.MaterialEntity;
import com.icebox.service.materials.domain.MaterialStockTransactionEntity;
import com.icebox.service.materials.domain.MaterialVariantEntity;
import com.icebox.service.materials.domain.TransactionGroupEntity;
import com.icebox.service.materials.dto.CreateTransactionGroupRequest;
import com.icebox.service.materials.enums.StockMovementType;
import com.icebox.service.materials.enums.TransactionGroupType;
import com.icebox.service.materials.repository.MaterialStockTransactionRepository;
import com.icebox.service.materials.repository.TransactionGroupRepository;
import com.icebox.service.materials.response.TransactionGroupDetailsResponse;
import com.icebox.service.materials.response.TransactionListItem;
import com.icebox.service.utils.SecurityUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaterialTransactionService {

    private final TransactionGroupRepository transactionGroupRepository;
    private final MaterialStockTransactionRepository transactionRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public UUID createTransactionGroupWithTransactions(TransactionGroupEntity group, List<MaterialStockTransactionEntity> transactions) {
        // 1. persist group
        transactionGroupRepository.save(group);

        // 2. persist transactions
        transactionRepository.saveAll(transactions);

        // 3. return group id for reference
        return group.getId();
    }

    /**
     * Ensures quantityDelta sign matches movementType:
     * - IN  => positive
     * - OUT => negative
     */
    private int normalizeQuantityDelta(StockMovementType movementType, int rawQuantityDelta) {
        int abs = Math.abs(rawQuantityDelta);
        if (movementType == StockMovementType.OUT) {
            return -abs;
        }
        // For IN (and any future "incoming" types), ensure positive
        return abs;
    }

    @Transactional
    public UUID createTransactionGroup(CreateTransactionGroupRequest request) {
        request.setTenantId(getTenantId());
        UUID groupId = UUID.randomUUID();

        TransactionGroupEntity group = TransactionGroupEntity.builder()
                .id(groupId)
                .tenantId(request.getTenantId())
                .groupType(request.getGroupType())
                .referenceId(request.getReferenceId())
                .referenceType(request.getReferenceType())
                .remarks(request.getRemarks())
                .createdByUserId(request.getCreatedByUserId())
                .createdAt(Instant.now())
                .build();

        transactionGroupRepository.save(group);

        List<MaterialStockTransactionEntity> transactions = request.getTransactions().stream()
                .map(t -> {
                    int normalizedDelta = normalizeQuantityDelta(t.getMovementType(), t.getQuantityDelta());

                    return MaterialStockTransactionEntity.builder()
                            .tenantId(request.getTenantId())
                            .material(entityManager.getReference(MaterialEntity.class, t.getMaterialId()))
                            .variant(t.getVariantId() == null
                                    ? null
                                    : entityManager.getReference(MaterialVariantEntity.class, t.getVariantId()))
                            .movementType(t.getMovementType())
                            .quantityDelta(normalizedDelta)
                            .transactionDate(t.getTransactionDate())
                            .sourceType(t.getSourceType())
                            .sourceId(t.getSourceId())
                            .remarks(t.getRemarks())
                            .transactionGroupId(groupId)
                            .createdByUserId(request.getCreatedByUserId())
                            .createdAt(Instant.now())
                            .build();
                })
                .toList();

        transactionRepository.saveAll(transactions);

        return groupId;
    }

    @Transactional(readOnly = true)
    public Page<TransactionListItem> listTransactions( Long materialId, TransactionGroupType groupType, Instant from, Instant to, Pageable pageable) {
        return transactionRepository.findTransactions(getTenantId(), materialId, groupType, from, to, pageable);
    }

    @Transactional(readOnly = true)
    public TransactionGroupDetailsResponse getTransactionGroupDetails(UUID groupId) {

        TransactionGroupEntity group = transactionGroupRepository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("Transaction group not found"));

        List<TransactionGroupDetailsResponse.TransactionLineItem> items = transactionRepository.findGroupLineItems(groupId);

        return new TransactionGroupDetailsResponse(group.getId(), group.getGroupType(), group.getReferenceId(), group.getReferenceType(), group.getRemarks(), group.getCreatedAt(), items);
    }

    private String getTenantId() {
        return SecurityUtils.getClaim("tid"); // tenantId
    }
}

