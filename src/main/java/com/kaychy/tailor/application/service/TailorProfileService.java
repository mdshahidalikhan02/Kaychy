package com.kaychy.tailor.application.service;

import com.kaychy.common.exception.ResourceConflictException;
import com.kaychy.common.exception.ResourceNotFoundException;
import com.kaychy.tailor.application.dto.AddTailorSkillRequest;
import com.kaychy.tailor.application.dto.CreateTailorProfileRequest;
import com.kaychy.tailor.application.dto.TailorProfileResponse;
import com.kaychy.tailor.application.dto.TailorSkillResponse;
import com.kaychy.tailor.domain.entity.Skill;
import com.kaychy.tailor.domain.entity.TailorProfile;
import com.kaychy.tailor.domain.entity.TailorSkill;
import com.kaychy.tailor.domain.entity.TailorSkillId;
import com.kaychy.tailor.domain.repository.SkillRepository;
import com.kaychy.tailor.domain.repository.TailorProfileRepository;
import com.kaychy.tailor.domain.repository.TailorSkillRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TailorProfileService {

    private final TailorProfileRepository tailorProfileRepository;
    private final SkillRepository skillRepository;
    private final TailorSkillRepository tailorSkillRepository;

    public TailorProfileService(TailorProfileRepository tailorProfileRepository, SkillRepository skillRepository,
                                TailorSkillRepository tailorSkillRepository) {
        this.tailorProfileRepository = tailorProfileRepository;
        this.skillRepository = skillRepository;
        this.tailorSkillRepository = tailorSkillRepository;
    }

    @Transactional
    public TailorProfileResponse createProfileForUser(UUID userId, CreateTailorProfileRequest request) {
        if (tailorProfileRepository.findByUserId(userId).isPresent()) {
            throw new ResourceConflictException("A tailor profile already exists for this user");
        }
        TailorProfile saved = tailorProfileRepository.save(TailorProfile.create(
                userId, request.businessName(), request.bio(), request.yearsOfExperience(),
                request.city(), request.state(), request.country(), request.latitude(), request.longitude()));
        return toProfileResponse(saved);
    }

    @Transactional
    public TailorSkillResponse addOrUpdateSkill(UUID tailorId, AddTailorSkillRequest request) {
        requireTailorExists(tailorId);
        Skill skill = skillRepository.findById(request.skillId())
                .orElseThrow(() -> new ResourceNotFoundException("No such skill: " + request.skillId()));

        TailorSkillId id = new TailorSkillId(tailorId, skill.getId());
        TailorSkill tailorSkill = tailorSkillRepository.findById(id)
                .map(existing -> {
                    existing.updateProficiency(request.proficiencyLevel(), request.yearsOfExperience());
                    return existing;
                })
                .orElseGet(() -> TailorSkill.create(tailorId, skill.getId(), request.proficiencyLevel(), request.yearsOfExperience()));

        TailorSkill saved = tailorSkillRepository.save(tailorSkill);
        return new TailorSkillResponse(skill.getId(), skill.getName(), saved.getProficiencyLevel(), saved.getYearsOfExperience());
    }

    @Transactional(readOnly = true)
    public List<TailorSkillResponse> getSkills(UUID tailorId) {
        return tailorSkillRepository.findById_TailorId(tailorId).stream()
                .map(ts -> {
                    Skill skill = skillRepository.findById(ts.getId().getSkillId())
                            .orElseThrow(() -> new ResourceNotFoundException("Skill data missing for id " + ts.getId().getSkillId()));
                    return new TailorSkillResponse(skill.getId(), skill.getName(), ts.getProficiencyLevel(), ts.getYearsOfExperience());
                })
                .collect(Collectors.toList());
    }

    private void requireTailorExists(UUID tailorId) {
        if (!tailorProfileRepository.existsById(tailorId)) {
            throw new ResourceNotFoundException("No such tailor profile: " + tailorId);
        }
    }

    private TailorProfileResponse toProfileResponse(TailorProfile profile) {
        return new TailorProfileResponse(profile.getId(), profile.getUserId(), profile.getBusinessName(),
                profile.getBio(), profile.getYearsOfExperience(), profile.getCity(), profile.getState(),
                profile.getCountry(), profile.isActive());
    }
}