package com.kaychy.customer.domain.repository;

import java.util.List;
import java.util.UUID;

import com.kaychy.customer.domain.enitity.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, UUID> {

    List<CustomerAddress> findByCustomerProfileId(UUID customerProfileId);
}
