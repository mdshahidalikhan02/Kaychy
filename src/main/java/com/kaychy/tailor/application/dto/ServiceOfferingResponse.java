package com.kaychy.tailor.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ServiceOfferingResponse(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        String currency,
        Integer estimatedTurnaroundDays,
        boolean active) {
}
