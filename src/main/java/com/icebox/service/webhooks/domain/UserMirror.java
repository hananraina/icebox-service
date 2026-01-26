package com.icebox.service.webhooks.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users_mirror")
@Getter
@Setter
public class UserMirror {
    @Id
    private UUID id;

    @Column(name = "full_name")
    private String fullName;

    private String email;

    @JdbcTypeCode(SqlTypes.ARRAY) // Hibernate 6 annotation
    private List<String> roles;

    // Getters and Setters
}
