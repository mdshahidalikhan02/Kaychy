package com.kaychy.tailor.application.dto;

import com.kaychy.tailor.domain.enums.AvailabilityStatus;
import java.time.Instant;
import java.util.UUID;

public record AvailabilitySlotResponse(
        UUID id,
        Instant startTime,
        Instant endTime,
        AvailabilityStatus status) {
}