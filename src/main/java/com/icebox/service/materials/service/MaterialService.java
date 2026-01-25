package com.icebox.service.materials.service;

import com.icebox.service.materials.domain.MaterialEntity;
import com.icebox.service.materials.domain.MaterialVariantEntity;
import com.icebox.service.materials.dto.*;
import com.icebox.service.materials.repository.MaterialRepository;
import com.icebox.service.materials.repository.MaterialStockTransactionRepository;
import com.icebox.service.materials.repository.MaterialStockTransactionRepository.MaterialStockSummary;
import com.icebox.service.utils.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final MaterialStockTransactionRepository materialStockTransactionRepository;

    @Transactional(readOnly = true)
    public List<MaterialResponse> getMaterials() {
        String tenantId = getTenantId();
        // 1. Load all materials
        List<MaterialEntity> materials = materialRepository.findAllByTenantId(tenantId);

        // 2. Load stock aggregated per material
        List<MaterialStockSummary> stockSummaries = materialStockTransactionRepository.findMaterialStockByTenantId(tenantId);

        Map<Long, Integer> stockByMaterialId = stockSummaries.stream().collect(Collectors.toMap(MaterialStockSummary::getMaterialId, MaterialStockSummary::getCurrentStock));

        // 3. Map to responses including currentStock (default 0 if none)
        return materials.stream().map(m -> MaterialMapper.toMaterialResponse(m, stockByMaterialId.getOrDefault(m.getId(), 0))).toList();
    }

    @Transactional
    public MaterialResponse createMaterial(CreateMaterialRequestDTO request) {
        String tenantId = getTenantId();
        MaterialEntity material = MaterialMapper.toMaterialEntity(request, tenantId);
        materialRepository.save(material);
        // Newly created material has no stock yet → 0
        return MaterialMapper.toMaterialResponse(material, 0);
    }

    public void deleteMaterial(Long materialId) {
        String tenantId = getTenantId();
        // 1. Check if material exists
        MaterialEntity material = materialRepository.findByIdAndTenantId(materialId, tenantId).orElseThrow(() -> new EntityNotFoundException("Material with id " + materialId + " not found"));

        // 2. Check if material is in transactions
        boolean hasTransactions = materialStockTransactionRepository.existsByTenantIdAndMaterialId(tenantId, materialId);
        if (hasTransactions) {
            // You may want to define a custom exception type instead of IllegalStateException
            throw new IllegalStateException("Material with id " + materialId + " has transactions and cannot be deleted");
        }
        // 3. Safe to delete
        materialRepository.deleteByIdAndTenantId(materialId, tenantId);
    }

    public MaterialResponse updateMaterial(Long materialId, MaterialUpdateRequestDTO dto) {
        String tenantId = getTenantId();
        MaterialEntity material = materialRepository
                .findById(materialId)
                .filter(m -> m.getTenantId().equals(tenantId))
                .orElseThrow(() -> new IllegalStateException("Material not found"));

        if (dto.getName() != null) material.setName(dto.getName());
        if (dto.getType() != null) material.setType(dto.getType());
        if (dto.getUnit() != null) material.setUnit(dto.getUnit());
        if (dto.getDescription() != null) material.setDescription(dto.getDescription());
        if (dto.getIsActive() != null) material.setIsActive(dto.getIsActive());

        Map<Long, MaterialVariantEntity> existingVariants =
                material.getVariants().stream()
                        .collect(Collectors.toMap(MaterialVariantEntity::getId, v -> v));

        if (dto.getVariants() != null) {
            for (MaterialVariantUpdateDTO vDto : dto.getVariants()) {

                if (vDto.getId() == null) {
                    // create new variant
                    MaterialVariantEntity variant = new MaterialVariantEntity();
                    variant.setTenantId(tenantId);
                    variant.setMaterial(material);
                    variant.setVariantName(vDto.getName());
                    variant.setDescription(vDto.getDescription());
                    variant.setIsActive(
                            vDto.getIsActive() != null ? vDto.getIsActive() : true
                    );
                    material.getVariants().add(variant);

                } else {
                    // update existing variant
                    MaterialVariantEntity variant = existingVariants.get(vDto.getId());
                    if (variant == null) {
                        throw new IllegalStateException("Variant not found");
                    }

                    if (vDto.getName() != null)
                        variant.setVariantName(vDto.getName());
                    if (vDto.getDescription() != null)
                        variant.setDescription(vDto.getDescription());
                    if (vDto.getIsActive() != null)
                        variant.setIsActive(vDto.getIsActive());
                }
            }
        }

        materialRepository.save(material);
        return MaterialMapper.toMaterialResponse(material, materialStockTransactionRepository.getCurrentStockByMaterialIdAndTenantId(materialId, tenantId));
    }

    public void addVariants(Long materialId, List<CreateVariantRequestDTO> variants) {
        String tenantId = getTenantId();
        MaterialEntity material = materialRepository.findByIdAndTenantId(materialId, tenantId).orElseThrow(() -> new EntityNotFoundException("Material not found"));

        if (material.getVariants() == null) {
            material.setVariants(new java.util.ArrayList<>());
        }

        for (CreateVariantRequestDTO v : variants) {
            MaterialVariantEntity variant = MaterialMapper.toVariantEntity(v, tenantId, material);
            material.getVariants().add(variant);
        }
    }

    private String getTenantId() {
        return SecurityUtils.getClaim("tid"); // tenantId
    }
}