package com.icebox.service.materials.repository;

import com.icebox.service.materials.domain.MaterialStockTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MaterialStockTransactionRepository extends JpaRepository<MaterialStockTransactionEntity, Long> {

    @Query("""
            SELECT t.material.id AS materialId,
                   COALESCE(SUM(t.quantityChange), 0) AS currentStock
            FROM MaterialStockTransactionEntity t
            WHERE t.tenantId = :tenantId
            GROUP BY t.material.id
            """)
    List<MaterialStockSummary> findMaterialStockByTenantId(String tenantId);

    @Query("""
            SELECT COALESCE(SUM(t.quantityChange), 0)
            FROM MaterialStockTransactionEntity t
            WHERE t.material.id = :materialId AND t.tenantId = :tenantId
            """)
    Integer getCurrentStockByMaterialIdAndTenantId(Long materialId, String tenantId);

    Boolean existsByTenantIdAndMaterialId(String tenantId, Long materialId);
    boolean existsByTenantIdAndMaterialIdAndVariantId(String tenantId, Long materialId, Long variantId);
    interface MaterialStockSummary {
        Long getMaterialId();

        Integer getCurrentStock();
    }
    
    

}
