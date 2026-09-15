package com.kaychy.tailor.domain.repository;

import java.util.List;
import java.util.UUID;

import com.kaychy.tailor.domain.entity.TailorServiceOffering;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TailorServiceOfferingRepository extends JpaRepository<TailorServiceOffering, UUID> {

    List<TailorServiceOffering> findByTailorId(UUID tailorId);
}
