package com.kaychy.tailor.application.dto;

import java.util.UUID;

public record TailorProfileResponse(
        UUID id,
        UUID userId,
        String businessName,
        String bio,
        Integer yearsOfExperience,
        String city,
        String state,
        String country,
        boolean active) {
}
