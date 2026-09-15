package com.kaychy.customer.application.dto;

import jakarta.validation.constraints.NotBlank;

public record AddAddressRequest(
        @NotBlank String label,
        @NotBlank String line1,
        String line2,
        @NotBlank String city,
        @NotBlank String state,
        @NotBlank String postalCode,
        @NotBlank String country,
        Double latitude,
        Double longitude,
        boolean makeDefault) {
}