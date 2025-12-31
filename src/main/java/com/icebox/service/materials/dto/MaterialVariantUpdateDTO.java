package com.icebox.service.materials.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialVariantUpdateDTO {

    private Long id;              // null → create
    private String variantName;
    private String description;
    private Boolean isActive;
}
