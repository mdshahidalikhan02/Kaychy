package com.kaychy.customer.application.service;

import com.kaychy.common.exception.ResourceConflictException;
import com.kaychy.customer.application.dto.AddAddressRequest;
import com.kaychy.customer.application.dto.AddressResponse;
import com.kaychy.customer.application.dto.CustomerProfileResponse;
import com.kaychy.customer.domain.enitity.CustomerAddress;
import com.kaychy.customer.domain.repository.CustomerAddressRepository;
import com.kaychy.customer.domain.enitity.CustomerProfile;
import com.kaychy.customer.domain.repository.CustomerProfileRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerProfileService {

    private final CustomerProfileRepository customerProfileRepository;
    private final CustomerAddressRepository customerAddressRepository;

    public CustomerProfileService(CustomerProfileRepository customerProfileRepository,
                                  CustomerAddressRepository customerAddressRepository) {
        this.customerProfileRepository = customerProfileRepository;
        this.customerAddressRepository = customerAddressRepository;
    }

    @Transactional
    public CustomerProfileResponse createProfileForUser(UUID userId) {
        if (customerProfileRepository.findByUserId(userId).isPresent()) {
            throw new ResourceConflictException("A customer profile already exists for this user");
        }
        CustomerProfile saved = customerProfileRepository.save(CustomerProfile.createFor(userId));
        return new CustomerProfileResponse(saved.getId(), saved.getUserId());
    }

    @Transactional
    public AddressResponse addAddress(UUID customerProfileId, AddAddressRequest request) {
        if (request.makeDefault()) {
            List<CustomerAddress> existing = customerAddressRepository.findByCustomerProfileId(customerProfileId);
            existing.forEach(CustomerAddress::unmarkAsDefault);
            customerAddressRepository.saveAll(existing);
        }

        CustomerAddress address = CustomerAddress.create(
                customerProfileId,
                request.label(),
                request.line1(),
                request.line2(),
                request.city(),
                request.state(),
                request.postalCode(),
                request.country(),
                request.latitude(),
                request.longitude());

        if (request.makeDefault()) {
            address.markAsDefault();
        }

        CustomerAddress saved = customerAddressRepository.save(address);
        return toAddressResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<AddressResponse> getAddresses(UUID customerProfileId) {
        return customerAddressRepository.findByCustomerProfileId(customerProfileId).stream()
                .map(this::toAddressResponse)
                .collect(Collectors.toList());
    }

    private AddressResponse toAddressResponse(CustomerAddress address) {
        return new AddressResponse(
                address.getId(),
                address.getLabel(),
                address.getLine1(),
                address.getLine2(),
                address.getCity(),
                address.getState(),
                address.getPostalCode(),
                address.getCountry(),
                address.isDefault());
    }
}