package com.icebox.service.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.postgresql.util.PSQLException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDuplicateKey(DataIntegrityViolationException ex) {

        Throwable cause = ex.getCause();
        if (cause instanceof ConstraintViolationException cve &&
                cve.getCause() instanceof PSQLException psqlEx) {

            String detail = psqlEx.getServerErrorMessage().getDetail();
            // Example:
            // Key (tenant_id, name)=(uuid, Box) already exists.

            if (detail != null && detail.contains("already exists")) {
                String value = detail.substring(
                        detail.indexOf(",") + 2,
                        detail.indexOf(")")
                );
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(value + " already exists");
            }
        }

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Duplicate value violates unique constraint");
    }

}
