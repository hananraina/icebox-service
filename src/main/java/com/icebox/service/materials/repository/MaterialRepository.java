package com.icebox.service.materials.repository;

import com.icebox.service.materials.domain.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MaterialRepository
        extends JpaRepository<MaterialEntity, Long> {

    List<MaterialEntity> findAllByTenantId(String tenantId);
    Optional<MaterialEntity> findByIdAndTenantId(Long id, String tenantId);
    boolean existsByIdAndTenantId(Long materialId, String tenantId);
    void deleteByIdAndTenantId(Long materialId, String tenantId);
}


