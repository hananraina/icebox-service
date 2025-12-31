package com.icebox.service.materials.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MaterialUpdateRequestDTO {

    private String name;
    private String type;
    private String unit;
    private String description;
    private Boolean isActive;

    private List<MaterialVariantUpdateDTO> variants;
}

