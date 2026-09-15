package com.kaychy.tailor.domain.repository;

import java.util.List;
import java.util.UUID;

import com.kaychy.tailor.domain.entity.TailorAvailabilitySlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TailorAvailabilitySlotRepository extends JpaRepository<TailorAvailabilitySlot, UUID> {

    List<TailorAvailabilitySlot> findByTailorId(UUID tailorId);
}
