package com.icebox.service.financialyear.repository;

import com.icebox.service.financialyear.domain.FinancialYearEntity;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface FinancialYearRepository extends CrudRepository<FinancialYearEntity, UUID> {

    @Override
    @NonNull
    List<FinancialYearEntity> findAll();

    boolean existsByCode(@NotNull String code);
}
