package com.icebox.service.materials.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "materials",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_materials_tenant_name",
                columnNames = {"tenant_id", "name"}
        )
)
@Getter @Setter
public class MaterialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tenantId;

    @Column(nullable = false)
    private String name;

    private String type;
    private String unit;
    private String description;

    private Boolean isActive = true;

//      We dont need it yet
//    @Column(name="last_updated", nullable = false)
//    private LocalDateTime lastUpdated;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MaterialVariantEntity> variants = new ArrayList<>();
}

