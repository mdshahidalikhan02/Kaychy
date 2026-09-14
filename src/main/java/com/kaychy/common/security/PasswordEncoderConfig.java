package com.kaychy.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Provides password hashing only (spring-security-crypto). This does NOT
 * pull in Spring Security's filter chain / auto-configuration — that's
 * deliberately deferred to Phase 9 (Security and Hardening) per the
 * project plan, once JWT auth is actually being built.
 */
@Configuration
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
