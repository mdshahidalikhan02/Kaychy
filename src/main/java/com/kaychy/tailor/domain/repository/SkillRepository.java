package com.kaychy.tailor.domain.repository;

import com.kaychy.tailor.domain.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
