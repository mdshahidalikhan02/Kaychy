package com.kaychy.tailor.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.kaychy.tailor.domain.entity.TailorSkill;
import com.kaychy.tailor.domain.entity.TailorSkillId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TailorSkillRepository extends JpaRepository<TailorSkill, TailorSkillId> {

    // Spring Data reads the underscore as "go into the id field, then read tailorId"
    List<TailorSkill> findById_TailorId(UUID tailorId);

    Optional<TailorSkill> findById(TailorSkillId id);
}