package com.kaychy.auth.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Input for {@code UserRegistrationService.register}. The raw password
 * lives here only long enough to be hashed — it is never persisted as-is.
 */
public record RegisterUserRequest(
        @NotBlank @Email @Size(max = 255) String email,
        @NotBlank @Size(min = 8, max = 100) String password,
        @NotBlank @Size(max = 100) String firstName,
        @Size(max = 100) String lastName,
        @Size(max = 30) String phoneNumber) {
}
