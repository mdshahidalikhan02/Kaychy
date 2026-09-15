package com.kaychy.tailor.application.dto;

import com.kaychy.tailor.domain.enums.ProficiencyLevel;

public record TailorSkillResponse(
        Long skillId,
        String skillName,
        ProficiencyLevel proficiencyLevel,
        Integer yearsOfExperience) {
}