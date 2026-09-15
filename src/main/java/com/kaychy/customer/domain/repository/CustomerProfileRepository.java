package com.kaychy.customer.domain.repository;

import java.util.Optional;
import java.util.UUID;

import com.kaychy.customer.domain.enitity.CustomerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, UUID> {

    Optional<CustomerProfile> findByUserId(UUID userId);
}
