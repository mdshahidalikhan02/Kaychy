package com.kaychy.tailor.application.service;

import com.kaychy.common.exception.ResourceNotFoundException;
import com.kaychy.tailor.application.dto.PublishServiceOfferingRequest;
import com.kaychy.tailor.application.dto.ServiceOfferingResponse;
import com.kaychy.tailor.domain.entity.TailorServiceOffering;
import com.kaychy.tailor.domain.repository.TailorProfileRepository;
import com.kaychy.tailor.domain.repository.TailorServiceOfferingRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Handles a tailor's publishable services (self-serve: no admin approval step). */
@Service
public class TailorServiceOfferingService {

    private final TailorProfileRepository tailorProfileRepository;
    private final TailorServiceOfferingRepository offeringRepository;

    public TailorServiceOfferingService(TailorProfileRepository tailorProfileRepository,
                                        TailorServiceOfferingRepository offeringRepository) {
        this.tailorProfileRepository = tailorProfileRepository;
        this.offeringRepository = offeringRepository;
    }

    @Transactional
    public ServiceOfferingResponse publish(UUID tailorId, PublishServiceOfferingRequest request) {
        if (!tailorProfileRepository.existsById(tailorId)) {
            throw new ResourceNotFoundException("No such tailor profile: " + tailorId);
        }
        TailorServiceOffering saved = offeringRepository.save(TailorServiceOffering.create(
                tailorId, request.name(), request.description(), request.price(),
                request.currency(), request.estimatedTurnaroundDays()));
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<ServiceOfferingResponse> getOfferings(UUID tailorId) {
        return offeringRepository.findByTailorId(tailorId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ServiceOfferingResponse toResponse(TailorServiceOffering offering) {
        return new ServiceOfferingResponse(offering.getId(), offering.getName(), offering.getDescription(),
                offering.getPrice(), offering.getCurrency(), offering.getEstimatedTurnaroundDays(), offering.isActive());
    }
}