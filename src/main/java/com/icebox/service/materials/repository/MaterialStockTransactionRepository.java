package com.icebox.service.materials.repository;

import com.icebox.service.materials.domain.MaterialStockTransactionEntity;
import com.icebox.service.materials.enums.TransactionGroupType;
import com.icebox.service.materials.response.TransactionGroupDetailsResponse;
import com.icebox.service.materials.response.TransactionListItem;
import com.icebox.service.webhooks.domain.UserMirror;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


public interface MaterialStockTransactionRepository extends JpaRepository<MaterialStockTransactionEntity, Long> {

    @Query("""
            SELECT t.material.id AS materialId,
                   COALESCE(SUM(t.quantityDelta), 0) AS currentStock
            FROM MaterialStockTransactionEntity t
            WHERE t.tenantId = :tenantId
            GROUP BY t.material.id
            """)
    List<MaterialStockSummary> findMaterialStockByTenantId(String tenantId);

    @Query("""
            SELECT COALESCE(SUM(t.quantityDelta), 0)
            FROM MaterialStockTransactionEntity t
            WHERE t.material.id = :materialId AND t.tenantId = :tenantId
            """)
    Integer getCurrentStockByMaterialIdAndTenantId(Long materialId, String tenantId);

    Boolean existsByTenantIdAndMaterialId(String tenantId, Long materialId);

    boolean existsByTenantIdAndMaterialIdAndVariantId(String tenantId, Long materialId, Long variantId);

    List<MaterialStockTransactionEntity> findByTransactionGroupId(UUID transactionGroupId);

    @Query("""
            SELECT new com.icebox.service.materials.response.TransactionListItem(
                t.id,
                t.transactionDate,
                m.id,
                m.name,
                v.variantName,
                t.movementType,
                t.quantityDelta,
                g.groupType,
                g.referenceId,
                u.fullName,
                t.sourceType,
                t.sourceId
            )
            FROM MaterialStockTransactionEntity t
            JOIN t.material m
            LEFT JOIN t.variant v
            JOIN TransactionGroupEntity g
              ON g.id = t.transactionGroupId
            LEFT JOIN UserMirror u
              ON u.id = t.createdByUserId
            WHERE t.tenantId = :tenantId
              AND (:materialId IS NULL OR m.id = :materialId)
              AND (:groupType IS NULL OR g.groupType = :groupType)
              AND t.transactionDate >= COALESCE(:from, t.transactionDate)
              AND t.transactionDate <= COALESCE(:to,   t.transactionDate)
            ORDER BY t.transactionDate DESC
            """)
    Page<TransactionListItem> findTransactions(String tenantId, Long materialId, TransactionGroupType groupType, Instant from, Instant to, Pageable pageable);

    @Query("""
            SELECT new com.icebox.service.materials.response.TransactionGroupDetailsResponse$TransactionLineItem(
                t.id,
                t.transactionDate,
                m.id,
                m.name,
                v.variantName,
                t.movementType,
                t.quantityDelta,
                t.sourceType,
                t.sourceId
            )
            FROM MaterialStockTransactionEntity t
            JOIN t.material m
            LEFT JOIN t.variant v
            WHERE t.transactionGroupId = :groupId
            ORDER BY t.transactionDate
            """)
    List<TransactionGroupDetailsResponse.TransactionLineItem> findGroupLineItems(UUID groupId);

    interface MaterialStockSummary {
        Long getMaterialId();

        Integer getCurrentStock();
    }

}
