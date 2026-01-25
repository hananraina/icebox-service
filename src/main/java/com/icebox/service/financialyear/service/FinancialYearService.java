package com.icebox.service.financialyear.service;

import com.icebox.service.financialyear.domain.FinancialYearEntity;
import com.icebox.service.financialyear.dto.FinancialYearRequest;
import com.icebox.service.financialyear.repository.FinancialYearRepository;
import com.icebox.service.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class FinancialYearService {
    @Autowired
    private FinancialYearRepository repository;

    public List<FinancialYearEntity> getAllFinancialYears() {
        return repository.findAll();
    }

    public FinancialYearEntity createFinancialYear(FinancialYearRequest request) {
        request.setTenantId(getTenantId());
        if (repository.existsByCode(request.getCode())) {
            throw new IllegalArgumentException("Financial year with code " + request.getCode() + " already exists");
        }
        // TODO: fix the UUID
        FinancialYearEntity entity = FinancialYearEntity.builder()
                .code(request.getCode())
                .displayName(request.getDisplayName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .isLocked(false)
                .createdAt(Instant.now())
                .createdBy(request.getCreatedBy())
                .tenantId(request.getTenantId())
                .build();

        return repository.save(entity);
    }
    private String getTenantId() {
        return SecurityUtils.getClaim("tid");
    }
}
