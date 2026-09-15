package com.kaychy.tailor.domain.entity;

import com.kaychy.tailor.domain.enums.ProficiencyLevel;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "tailor_skills")
public class TailorSkill {

    @EmbeddedId
    private TailorSkillId id;

    @Enumerated(EnumType.STRING)
    @Column(name = "proficiency_level", nullable = false, length = 20)
    private ProficiencyLevel proficiencyLevel;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    protected TailorSkill() {
        // required by JPA
    }

    private TailorSkill(TailorSkillId id, ProficiencyLevel proficiencyLevel, Integer yearsOfExperience) {
        this.id = id;
        this.proficiencyLevel = proficiencyLevel;
        this.yearsOfExperience = yearsOfExperience;
    }

    public static TailorSkill create(UUID tailorId, Long skillId, ProficiencyLevel proficiencyLevel, Integer yearsOfExperience) {
        return new TailorSkill(new TailorSkillId(tailorId, skillId), proficiencyLevel, yearsOfExperience);
    }

    public void updateProficiency(ProficiencyLevel proficiencyLevel, Integer yearsOfExperience) {
        this.proficiencyLevel = proficiencyLevel;
        this.yearsOfExperience = yearsOfExperience;
    }

    public TailorSkillId getId() { return id; }
    public ProficiencyLevel getProficiencyLevel() { return proficiencyLevel; }
    public Integer getYearsOfExperience() { return yearsOfExperience; }
}
