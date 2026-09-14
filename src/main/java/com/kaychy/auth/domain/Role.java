package com.kaychy.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Maps to the {@code roles} table created in V1__initial_schema.sql.
 * Roles are seeded by the migration (CUSTOMER, TAILOR, ADMIN) rather
 * than created by the application, so this entity is intentionally
 * read-mostly.
 */
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private RoleName name;

    protected Role() {
        // required by JPA
    }

    private Role(RoleName name) {
        this.name = name;
    }

    /**
     * Factory for tests and any future seeding logic. In normal
     * operation, roles are read via {@link RoleRepository}, not created.
     */
    public static Role of(RoleName name) {
        return new Role(name);
    }

    public Long getId() {
        return id;
    }

    public RoleName getName() {
        return name;
    }
}
