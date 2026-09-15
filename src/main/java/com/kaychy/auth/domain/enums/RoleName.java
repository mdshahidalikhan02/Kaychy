package com.kaychy.auth.domain.enums;

/**
 * The fixed set of roles seeded by V1__initial_schema.sql.
 * Kept as an enum (instead of free-form strings) so role checks
 * throughout the codebase are compile-time safe.
 */
public enum RoleName {
    CUSTOMER,
    TAILOR,
    ADMIN
}
