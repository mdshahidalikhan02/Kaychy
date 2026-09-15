package com.kaychy.tailor.domain.repository;

import java.util.Optional;
import java.util.UUID;

import com.kaychy.tailor.domain.entity.TailorProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TailorProfileRepository extends JpaRepository<TailorProfile, UUID> {

    Optional<TailorProfile> findByUserId(UUID userId);
}