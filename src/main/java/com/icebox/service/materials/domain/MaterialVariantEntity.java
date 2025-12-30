package com.icebox.service.materials.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "material_variants",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_variant_per_material",
                columnNames = {"tenant_id", "material_id", "variant_name"}
        )
)
@Getter @Setter
public class MaterialVariantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tenantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private MaterialEntity material;

    @Column(nullable = false)
    private String variantName;

    private String description;
    private Boolean isActive = true;

}

