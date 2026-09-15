package com.kaychy.tailor.application.dto;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;

public record AddAvailabilitySlotRequest(
        @NotNull Instant startTime,
        @NotNull Instant endTime) {
}
