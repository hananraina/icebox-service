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
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(materialService.getMaterials());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateMaterialRequestDTO request) {
        return ResponseEntity.ok(materialService.createMaterial(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editMaterial(@PathVariable("id") Long materialId,@RequestBody MaterialUpdateRequestDTO dto) {
        return ResponseEntity.ok(materialService.updateMaterial(materialId, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long materialId) {
        materialService.deleteMaterial(materialId);
        return ResponseEntity.ok(Map.of("message", "Material deleted"));
    }

    @DeleteMapping("/{id}/variants/{variantId}")
    public ResponseEntity<?> deleteVariant(@PathVariable("id") Long materialId, @PathVariable("variantId") Long variantId) {
        variantService.deleteVariant(materialId, variantId);
        return ResponseEntity.ok(Map.of("message", "Variant deleted"));
    }

}
