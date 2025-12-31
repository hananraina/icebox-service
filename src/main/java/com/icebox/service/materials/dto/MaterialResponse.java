package com.icebox.service.materials.dto;

import java.util.List;

public record MaterialResponse(
        Long id,
        String name,
        String type,
        String unit,
        Integer currentStock,
        String description,
        List<MaterialVariantResponse> variants
) {}

