package com.icebox.service.materials.repository;

import com.icebox.service.materials.domain.TransactionGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionGroupRepository
        extends JpaRepository<TransactionGroupEntity, UUID> {
}

