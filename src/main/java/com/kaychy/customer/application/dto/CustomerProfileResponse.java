package com.kaychy.customer.application.dto;

import java.util.UUID;

public record CustomerProfileResponse(UUID id, UUID userId) {
}