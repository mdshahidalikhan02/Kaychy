package com.kaychy.tailor.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Read-mostly: rows come from the V2 migration's seed data, not from
 * application code — matches your "predefined catalog" answer.
 */
@Entity
@Table(name = "skills")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 255)
    private String description;

    protected Skill() {
        // required by JPA
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
}