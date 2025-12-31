package com.icebox.service.materials.dto;

import com.icebox.service.materials.enums.MaterialType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaterialDetailDTO {

    private Long id;
    private String name;
    private MaterialType type;
    private String unit;
    private BigDecimal currentStock;
    private Boolean isActive;
    private String description;
    private List<MaterialVariantDTO> variants;

//    private OffsetDateTime lastUpdated;
}

