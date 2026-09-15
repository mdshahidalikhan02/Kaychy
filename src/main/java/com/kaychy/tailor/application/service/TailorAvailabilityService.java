package com.kaychy.tailor.application.service;

import com.kaychy.common.exception.InvalidRequestException;
import com.kaychy.common.exception.ResourceNotFoundException;
import com.kaychy.tailor.application.dto.AddAvailabilitySlotRequest;
import com.kaychy.tailor.application.dto.AvailabilitySlotResponse;
import com.kaychy.tailor.domain.entity.TailorAvailabilitySlot;
import com.kaychy.tailor.domain.repository.TailorAvailabilitySlotRepository;
import com.kaychy.tailor.domain.repository.TailorAvailabilitySlotRepository;
import com.kaychy.tailor.domain.repository.TailorProfileRepository;
import com.kaychy.tailor.domain.repository.TailorProfileRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.kaychy.tailor.domain.entity.TailorAvailabilitySlot;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Handles a tailor's one-off availability windows. */
@Service
public class TailorAvailabilityService {

    private final TailorProfileRepository tailorProfileRepository;
    private final TailorAvailabilitySlotRepository slotRepository;

    public TailorAvailabilityService(TailorProfileRepository tailorProfileRepository,
                                     TailorAvailabilitySlotRepository slotRepository) {
        this.tailorProfileRepository = tailorProfileRepository;
        this.slotRepository = slotRepository;
    }

    @Transactional
    public AvailabilitySlotResponse addSlot(UUID tailorId, AddAvailabilitySlotRequest request) {
        if (!tailorProfileRepository.existsById(tailorId)) {
            throw new ResourceNotFoundException("No such tailor profile: " + tailorId);
        }
        if (!request.endTime().isAfter(request.startTime())) {
            throw new InvalidRequestException("Slot end time must be after start time");
        }
        TailorAvailabilitySlot saved = slotRepository.save(
                TailorAvailabilitySlot.create(tailorId, request.startTime(), request.endTime()));
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<AvailabilitySlotResponse> getSlots(UUID tailorId) {
        return slotRepository.findByTailorId(tailorId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private AvailabilitySlotResponse toResponse(TailorAvailabilitySlot slot) {
        return new AvailabilitySlotResponse(slot.getId(), slot.getStartTime(), slot.getEndTime(), slot.getStatus());
    }
}