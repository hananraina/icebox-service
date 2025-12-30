package com.icebox.service.materials.repository;

import com.icebox.service.materials.domain.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialRepository
        extends JpaRepository<MaterialEntity, Long> {

    List<MaterialEntity> findByTenantId(String tenantId);
}


