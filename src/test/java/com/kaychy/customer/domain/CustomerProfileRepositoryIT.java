package com.kaychy.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kaychy.auth.domain.entity.User;
import com.kaychy.auth.domain.repository.UserRepository;
import com.kaychy.customer.domain.enitity.CustomerAddress;
import com.kaychy.customer.domain.enitity.CustomerProfile;
import com.kaychy.customer.domain.repository.CustomerAddressRepository;
import com.kaychy.customer.domain.repository.CustomerProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerProfileRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerProfileRepository customerProfileRepository;

    @Autowired
    private CustomerAddressRepository customerAddressRepository;

    @Test
    void savesAndFindsProfileByUserId() {
        User user = userRepository.save(User.register("customer1@example.com", "hashed", "Ann", "Roy", null));

        CustomerProfile saved = customerProfileRepository.saveAndFlush(CustomerProfile.createFor(user.getId()));

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(customerProfileRepository.findByUserId(user.getId())).isPresent();
    }

    @Test
    void savesAddressLinkedToProfile() {
        User user = userRepository.save(User.register("customer2@example.com", "hashed", "Ben", "Lee", null));
        CustomerProfile profile = customerProfileRepository.save(CustomerProfile.createFor(user.getId()));

        CustomerAddress address = CustomerAddress.create(
                profile.getId(), "Home", "12 MG Road", null, "Bengaluru", "Karnataka", "560001", "India", null, null);
        customerAddressRepository.save(address);

        assertThat(customerAddressRepository.findByCustomerProfileId(profile.getId())).hasSize(1);
    }
}