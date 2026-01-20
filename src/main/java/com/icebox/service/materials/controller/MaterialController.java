package com.icebox.service.materials.controller;

import com.icebox.service.materials.dto.CreateMaterialRequestDTO;
import com.icebox.service.materials.dto.MaterialUpdateRequestDTO;
import com.icebox.service.materials.service.MaterialService;
import com.icebox.service.materials.service.VariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MaterialController {

    private final MaterialService materialService;
    private final VariantService variantService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestHeader("X-FusionAuth-TenantId") String tenantId) {
        return ResponseEntity.ok(materialService.getMaterials(tenantId));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateMaterialRequestDTO request, @RequestHeader("X-FusionAuth-TenantId") String tenantId) {
        return ResponseEntity.ok(materialService.createMaterial(request, tenantId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editMaterial(@PathVariable("id") Long materialId,@RequestBody MaterialUpdateRequestDTO dto, @RequestHeader("X-FusionAuth-TenantId") String tenantId) {
        return ResponseEntity.ok(materialService.updateMaterial(materialId, tenantId, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long materialId, @RequestHeader("X-FusionAuth-TenantId") String tenantId) {
        materialService.deleteMaterial(tenantId, materialId);
        return ResponseEntity.ok(Map.of("message", "Material deleted"));
    }

    @DeleteMapping("/{id}/variants/{variantId}")
    public ResponseEntity<?> deleteVariant(@PathVariable("id") Long materialId, @PathVariable("variantId") Long variantId, @RequestHeader("X-FusionAuth-TenantId") String tenantId) {
        variantService.deleteVariant(tenantId, materialId, variantId);
        return ResponseEntity.ok(Map.of("message", "Variant deleted"));
    }

}
