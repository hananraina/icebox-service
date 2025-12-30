package com.icebox.service.materials.repository;

import com.icebox.service.materials.domain.MaterialStockTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MaterialStockTransactionRepository
        extends JpaRepository<MaterialStockTransactionEntity, Long> {
}


