package com.kaychy.tailor.domain.entity;

import com.kaychy.tailor.domain.enums.AvailabilityStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tailor_availability_slots")
public class TailorAvailabilitySlot {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "tailor_id", nullable = false)
    private UUID tailorId;

    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @Column(name = "end_time", nullable = false)
    private Instant endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AvailabilityStatus status = AvailabilityStatus.AVAILABLE;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected TailorAvailabilitySlot() {
        // required by JPA
    }

    private TailorAvailabilitySlot(UUID tailorId, Instant startTime, Instant endTime) {
        this.tailorId = tailorId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static TailorAvailabilitySlot create(UUID tailorId, Instant startTime, Instant endTime) {
        return new TailorAvailabilitySlot(tailorId, startTime, endTime);
    }

    public void markBooked() { this.status = AvailabilityStatus.BOOKED; }
    public void cancel() { this.status = AvailabilityStatus.CANCELLED; }

    public UUID getId() { return id; }
    public UUID getTailorId() { return tailorId; }
    public Instant getStartTime() { return startTime; }
    public Instant getEndTime() { return endTime; }
    public AvailabilityStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}