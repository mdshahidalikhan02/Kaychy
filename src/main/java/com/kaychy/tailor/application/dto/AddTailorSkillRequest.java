package com.kaychy.tailor.application.dto;

import com.kaychy.tailor.domain.enums.ProficiencyLevel;
import jakarta.validation.constraints.NotNull;

public record AddTailorSkillRequest(
        @NotNull Long skillId,
        @NotNull ProficiencyLevel proficiencyLevel,
        Integer yearsOfExperience) {
}
