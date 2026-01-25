package com.icebox.service.materials.service;

import com.icebox.service.materials.repository.MaterialStockTransactionRepository;
import com.icebox.service.materials.repository.MaterialVariantRepository;
import com.icebox.service.utils.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VariantService {

    private final MaterialVariantRepository variantRepository;
    private final MaterialStockTransactionRepository materialStockTransactionRepository;

    @Transactional
    public void deleteVariant(Long materialId, Long variantId) {
        String tenantId = getTenantId();
        if(!variantRepository.existsByTenantIdAndMaterialIdAndId(tenantId, materialId, variantId))
            throw new EntityNotFoundException("Variant with id " + variantId + " not found");

        if(materialStockTransactionRepository.existsByTenantIdAndMaterialIdAndVariantId(tenantId, materialId, variantId))
            throw new IllegalStateException("Cannot delete variant id " + variantId + " as it is in transactions");

        variantRepository.deleteByTenantIdAndMaterialIdAndId(tenantId, materialId, variantId);

    }

    private String getTenantId() {
        return SecurityUtils.getClaim("tid");
    }
}
