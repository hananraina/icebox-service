package com.icebox.service.financialyear.controller;

import com.icebox.service.financialyear.dto.FinancialYearRequest;
import com.icebox.service.financialyear.service.FinancialYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/financial-year")
public class FinancialYearController {
    @Autowired
    private FinancialYearService financialYearService;

    @GetMapping
    public ResponseEntity<?> getFinancialYears() {
        return new ResponseEntity<>(
                financialYearService.getAllFinancialYears(),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createFinancialYear(
            @RequestBody FinancialYearRequest request) {
        request.setCreatedBy(UUID.fromString("4b0702e2-eb6f-4655-9c02-d1847becec2c"));
        return new ResponseEntity<>(
                financialYearService.createFinancialYear(request),
                HttpStatus.CREATED);
    }
}
