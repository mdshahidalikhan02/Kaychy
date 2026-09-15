package com.kaychy.tailor.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tailor_profiles")
public class TailorProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @Column(name = "business_name", nullable = false, length = 150)
    private String businessName;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    private String city;
    private String state;
    private String country;
    private Double latitude;
    private Double longitude;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected TailorProfile() {
        // required by JPA
    }

    private TailorProfile(UUID userId, String businessName, String bio, Integer yearsOfExperience,
                          String city, String state, String country, Double latitude, Double longitude) {
        this.userId = userId;
        this.businessName = businessName;
        this.bio = bio;
        this.yearsOfExperience = yearsOfExperience;
        this.city = city;
        this.state = state;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static TailorProfile create(UUID userId, String businessName, String bio, Integer yearsOfExperience,
                                       String city, String state, String country, Double latitude, Double longitude) {
        return new TailorProfile(userId, businessName, bio, yearsOfExperience, city, state, country, latitude, longitude);
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public String getBusinessName() { return businessName; }
    public String getBio() { return bio; }
    public Integer getYearsOfExperience() { return yearsOfExperience; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getCountry() { return country; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public boolean isActive() { return active; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}