package com.kaychy.tailor.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tailor_services")
public class TailorServiceOffering {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "tailor_id", nullable = false)
    private UUID tailorId;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(name = "estimated_turnaround_days", nullable = false)
    private Integer estimatedTurnaroundDays;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected TailorServiceOffering() {
        // required by JPA
    }

    private TailorServiceOffering(UUID tailorId, String name, String description, BigDecimal price,
                                  String currency, Integer estimatedTurnaroundDays) {
        this.tailorId = tailorId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.estimatedTurnaroundDays = estimatedTurnaroundDays;
    }

    public static TailorServiceOffering create(UUID tailorId, String name, String description, BigDecimal price,
                                               String currency, Integer estimatedTurnaroundDays) {
        return new TailorServiceOffering(tailorId, name, description, price, currency, estimatedTurnaroundDays);
    }

    public void deactivate() { this.active = false; }
    public void activate() { this.active = true; }

    public UUID getId() { return id; }
    public UUID getTailorId() { return tailorId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public String getCurrency() { return currency; }
    public Integer getEstimatedTurnaroundDays() { return estimatedTurnaroundDays; }
    public boolean isActive() { return active; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
