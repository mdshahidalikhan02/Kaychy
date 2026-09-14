package com.kaychy.auth.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * Runs against the Postgres instance started by docker-compose.yml
 * (localhost:5433), so Flyway actually applies V1__initial_schema.sql
 * and Hibernate validates the entities against real Postgres types
 * (UUID, TIMESTAMPTZ) instead of an in-memory H2 substitute.
 *
 * Each test method runs inside a transaction that Spring rolls back
 * automatically afterward (default @DataJpaTest behavior), so this
 * never leaves leftover rows in your dev database.
 *
 * Requires: docker compose up -d   (must already be running)
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void savesAndReloadsUserWithAssignedRole() {
        Role customerRole = roleRepository.findByName(RoleName.CUSTOMER).orElseThrow();

        User user = User.register("jane.doe@example.com", "hashed-password", "Jane", "Doe", "+911234567890");
        user.addRole(customerRole);

        User saved = userRepository.saveAndFlush(user);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getUpdatedAt()).isNotNull();

        User found = userRepository.findByEmail("jane.doe@example.com").orElseThrow();
        assertThat(found.isActive()).isTrue();
        assertThat(found.getRoles())
                .extracting(Role::getName)
                .containsExactly(RoleName.CUSTOMER);
    }

    @Test
    void existsByEmailDistinguishesKnownFromUnknownAddresses() {
        User user = User.register("known@example.com", "hashed-password", "Known", "Person", null);
        userRepository.save(user);

        assertThat(userRepository.existsByEmail("known@example.com")).isTrue();
        assertThat(userRepository.existsByEmail("unknown@example.com")).isFalse();
    }

    @Test
    void rolesAreSeededByTheV1Migration() {
        assertThat(roleRepository.findByName(RoleName.CUSTOMER)).isPresent();
        assertThat(roleRepository.findByName(RoleName.TAILOR)).isPresent();
        assertThat(roleRepository.findByName(RoleName.ADMIN)).isPresent();
    }
}