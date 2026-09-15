package com.kaychy.tailor.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kaychy.auth.domain.entity.User;
import com.kaychy.auth.domain.repository.UserRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import com.kaychy.tailor.domain.entity.*;
import com.kaychy.tailor.domain.enums.ProficiencyLevel;
import com.kaychy.tailor.domain.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TailorProfileRepositoryIT {

    @Autowired private UserRepository userRepository;
    @Autowired private TailorProfileRepository tailorProfileRepository;
    @Autowired private SkillRepository skillRepository;
    @Autowired private TailorSkillRepository tailorSkillRepository;
    @Autowired private TailorServiceOfferingRepository offeringRepository;
    @Autowired private TailorAvailabilitySlotRepository slotRepository;

    private TailorProfile newTailorProfile(String email) {
        User user = userRepository.save(User.register(email, "hashed", "Tai", "Lor", null));
        return tailorProfileRepository.save(
                TailorProfile.create(user.getId(), "Tai's Tailoring", "Bio", 5, "Bengaluru", "Karnataka", "India", null, null));
    }

    @Test
    void savesTailorProfileLinkedToUser() {
        TailorProfile profile = newTailorProfile("tailor1@example.com");
        assertThat(tailorProfileRepository.findByUserId(profile.getUserId())).isPresent();
    }

    @Test
    void assignsASeededSkillToATailor() {
        TailorProfile profile = newTailorProfile("tailor2@example.com");
        Skill embroidery = skillRepository.findAll().stream()
                .filter(s -> s.getName().equals("Embroidery"))
                .findFirst()
                .orElseThrow();

        TailorSkill tailorSkill = TailorSkill.create(profile.getId(), embroidery.getId(), ProficiencyLevel.ADVANCED, 3);
        tailorSkillRepository.save(tailorSkill);

        assertThat(tailorSkillRepository.findById_TailorId(profile.getId())).hasSize(1);
    }

    @Test
    void savesAServiceOffering() {
        TailorProfile profile = newTailorProfile("tailor3@example.com");
        TailorServiceOffering offering = TailorServiceOffering.create(
                profile.getId(), "Bridal Blouse Stitching", "desc", new BigDecimal("2500.00"), "INR", 7);

        TailorServiceOffering saved = offeringRepository.save(offering);
        assertThat(saved.getId()).isNotNull();
        assertThat(offeringRepository.findByTailorId(profile.getId())).hasSize(1);
    }

    @Test
    void savesAValidAvailabilitySlot() {
        TailorProfile profile = newTailorProfile("tailor4@example.com");
        Instant start = Instant.now().plus(1, ChronoUnit.DAYS);
        Instant end = start.plus(4, ChronoUnit.HOURS);

        slotRepository.save(TailorAvailabilitySlot.create(profile.getId(), start, end));

        assertThat(slotRepository.findByTailorId(profile.getId())).hasSize(1);
    }

    @Test
    void databaseRejectsASlotWhereEndIsBeforeStart() {
        TailorProfile profile = newTailorProfile("tailor5@example.com");
        Instant start = Instant.now();
        Instant invalidEnd = start.minus(1, ChronoUnit.HOURS);

        // Bypassing the service on purpose here, to prove the DB's own
        // CHECK constraint (chk_tailor_availability_time_range) really
        // works, independent of our Java-level validation.
        assertThatThrownBy(() ->
                slotRepository.saveAndFlush(TailorAvailabilitySlot.create(profile.getId(), start, invalidEnd)))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}