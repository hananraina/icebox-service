package com.icebox.service.materials.dto;

import com.icebox.service.materials.enums.MaterialType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MaterialDTO {
    private String name;
    private MaterialType type;
    private String unit;
    private Boolean isActive;
}


