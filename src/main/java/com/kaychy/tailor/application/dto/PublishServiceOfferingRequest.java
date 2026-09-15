package com.kaychy.tailor.application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record PublishServiceOfferingRequest(
        @NotBlank String name,
        String description,
        @NotNull @DecimalMin(value = "0.0") BigDecimal price,
        @NotBlank String currency,
        @NotNull @Positive Integer estimatedTurnaroundDays) {
}