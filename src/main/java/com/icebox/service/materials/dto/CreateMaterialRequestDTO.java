package com.icebox.service.materials.dto;

import com.icebox.service.materials.enums.MaterialType;

import java.util.List;

public record CreateMaterialRequestDTO(
        String name,
        MaterialType type,
        String unit,
        String description,
        List<CreateVariantRequestDTO> variants
) {}
