package com.kaychy.tailor.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.kaychy.common.exception.InvalidRequestException;
import com.kaychy.tailor.application.dto.AddAvailabilitySlotRequest;
import com.kaychy.tailor.application.service.TailorAvailabilityService;
import com.kaychy.tailor.domain.entity.TailorAvailabilitySlot;
import com.kaychy.tailor.domain.repository.TailorAvailabilitySlotRepository;
import com.kaychy.tailor.domain.repository.TailorProfileRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TailorAvailabilityServiceTest {

    @Mock private TailorProfileRepository tailorProfileRepository;
    @Mock private TailorAvailabilitySlotRepository slotRepository;

    private TailorAvailabilityService service() {
        return new TailorAvailabilityService(tailorProfileRepository, slotRepository);
    }

    @Test
    void rejectsSlotWhereEndIsNotAfterStart() {
        UUID tailorId = UUID.randomUUID();
        when(tailorProfileRepository.existsById(tailorId)).thenReturn(true);

        Instant start = Instant.now();
        AddAvailabilitySlotRequest request = new AddAvailabilitySlotRequest(start, start.minus(1, ChronoUnit.HOURS));

        assertThatThrownBy(() -> service().addSlot(tailorId, request))
                .isInstanceOf(InvalidRequestException.class);
    }

    @Test
    void createsSlotWhenTimesAreValid() {
        UUID tailorId = UUID.randomUUID();
        when(tailorProfileRepository.existsById(tailorId)).thenReturn(true);
        when(slotRepository.save(any(TailorAvailabilitySlot.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Instant start = Instant.now();
        AddAvailabilitySlotRequest request = new AddAvailabilitySlotRequest(start, start.plus(2, ChronoUnit.HOURS));

        assertThat(service().addSlot(tailorId, request).startTime()).isEqualTo(start);
    }
}