package com.kaychy.tailor.application.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateTailorProfileRequest(
        @NotBlank String businessName,
        String bio,
        Integer yearsOfExperience,
        String city,
        String state,
        String country,
        Double latitude,
        Double longitude) {
}