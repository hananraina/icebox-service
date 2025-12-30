package com.icebox.service.materials.controller;

import com.icebox.service.materials.domain.MaterialEntity;
import com.icebox.service.materials.enums.MaterialType;
import com.icebox.service.materials.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.JsonNode;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MaterialController {

    private final MaterialService materialService;

    @GetMapping
    public List<MaterialEntity> getAll(
            @RequestHeader("X-Tenant-Id") String tenantId) {
        return materialService.getMaterials(tenantId);
    }

    @PostMapping
    public MaterialEntity create(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @RequestBody JsonNode json) {
        String name = json.path("name").asString();
        MaterialType type =  MaterialType.valueOf(json.path("type").asString());
        String unit =  json.path("unit").asString();
        return materialService.createMaterial(tenantId, name, type, unit);
    }
}
