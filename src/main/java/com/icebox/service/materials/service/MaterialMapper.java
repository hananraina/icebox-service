package com.icebox.service.materials.service;

import com.icebox.service.materials.domain.MaterialEntity;
import com.icebox.service.materials.domain.MaterialVariantEntity;
import com.icebox.service.materials.dto.CreateMaterialRequestDTO;
import com.icebox.service.materials.dto.CreateVariantRequestDTO;
import com.icebox.service.materials.dto.MaterialResponse;
import com.icebox.service.materials.dto.MaterialVariantResponse;

import java.util.ArrayList;
import java.util.List;

public final class MaterialMapper {

    private MaterialMapper() {
        // utility class
    }

    public static MaterialEntity toMaterialEntity(CreateMaterialRequestDTO request, String tenantId) {
        MaterialEntity material = new MaterialEntity();
        material.setTenantId(tenantId);
        material.setName(request.name());
        material.setType(request.type().toString());
        material.setUnit(request.unit());
        material.setDescription(request.description());

        if (material.getVariants() == null) {
            material.setVariants(new ArrayList<>());
        }

        if (request.variants() != null) {
            for (CreateVariantRequestDTO v : request.variants()) {
                MaterialVariantEntity variant = toVariantEntity(v, tenantId, material);
                material.getVariants().add(variant);
            }
        }

        return material;
    }

    public static MaterialVariantEntity toVariantEntity(
            CreateVariantRequestDTO dto,
            String tenantId,
            MaterialEntity material
    ) {
        MaterialVariantEntity variant = new MaterialVariantEntity();
        variant.setTenantId(tenantId);
        variant.setVariantName(dto.name());
        variant.setDescription(dto.description());
        variant.setMaterial(material);
        return variant;
    }

    public static MaterialResponse toMaterialResponse(MaterialEntity material, Integer currentStock) {
        List<MaterialVariantResponse> variantResponses =
                material.getVariants() == null
                        ? new ArrayList<>()
                        : material.getVariants().stream()
                        .map(MaterialMapper::toMaterialVariantResponse)
                        .toList();

        return new MaterialResponse(
                material.getId(),
                material.getName(),
                material.getType(),
                material.getUnit(),
                currentStock,              // <-- currentStock included here
                material.getDescription(),
                variantResponses
        );
    }

    // Optional: convenience overload if you ever need it without stock
    public static MaterialResponse toMaterialResponse(MaterialEntity material) {
        return toMaterialResponse(material, 0);
    }

    public static MaterialVariantResponse toMaterialVariantResponse(MaterialVariantEntity variant) {
        return new MaterialVariantResponse(
                variant.getId(),
                variant.getVariantName(),
                variant.getDescription()
        );
    }
}