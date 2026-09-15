package com.kaychy.tailor.domain.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class TailorSkillId implements Serializable {

    private UUID tailorId;
    private Long skillId;

    protected TailorSkillId() {
        // required by JPA
    }

    public TailorSkillId(UUID tailorId, Long skillId) {
        this.tailorId = tailorId;
        this.skillId = skillId;
    }

    public UUID getTailorId() { return tailorId; }
    public Long getSkillId() { return skillId; }

    // equals/hashCode are mandatory for a composite ID class - JPA uses
    // them to compare and cache entities, not just for our own code.
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof TailorSkillId that)) return false;
        return Objects.equals(tailorId, that.tailorId) && Objects.equals(skillId, that.skillId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tailorId, skillId);
    }
}
