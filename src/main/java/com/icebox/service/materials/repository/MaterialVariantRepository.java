package com.icebox.service.materials.repository;

import com.icebox.service.materials.domain.MaterialVariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialVariantRepository
        extends JpaRepository<MaterialVariantEntity, Long> {

    List<MaterialVariantEntity> findByTenantIdAndMaterial_Id(
            String tenantId, Long materialId);
}

