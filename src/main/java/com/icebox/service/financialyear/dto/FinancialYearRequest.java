package com.icebox.service.financialyear.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinancialYearRequest {

    @NotBlank
    private String code;          // FY_2026_27

    @NotBlank
    private String displayName;   // 2026-27

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    private UUID createdBy;
    private String tenantId;
}
