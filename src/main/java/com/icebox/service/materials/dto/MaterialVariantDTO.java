package com.icebox.service.materials.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaterialVariantDTO {

    private Long id;
    private String name;
    private String unit;
    private String description;
    private Boolean isActive;
    private Integer currentStock;
}

