package com.icebox.service.materials.service;

import com.icebox.service.materials.domain.MaterialEntity;
import com.icebox.service.materials.dto.MaterialVariantDTO;
import com.icebox.service.materials.enums.MaterialType;
import com.icebox.service.materials.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MaterialService {

    private final MaterialRepository materialRepository;

    @Transactional(readOnly = true)
    public List<MaterialEntity> getMaterials(String tenantId) {
        return materialRepository.findByTenantId(tenantId);
    }

    public MaterialEntity createMaterial(String tenantId, String name, MaterialType type, String unit) {
        MaterialEntity material = new MaterialEntity();
        material.setTenantId(tenantId);
        material.setName(name);
        material.setType(type.toString());
        material.setUnit(unit);
        return materialRepository.save(material);
    }

    public MaterialEntity addVariantsToMaterial(String tenantId, Long materialId, List<MaterialVariantDTO> variantNames) {
        return null;
    }
}


