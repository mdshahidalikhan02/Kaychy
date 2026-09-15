package com.kaychy.auth.application.service;

import com.kaychy.auth.application.dto.RegisterUserRequest;
import com.kaychy.auth.application.dto.UserResponse;
import com.kaychy.auth.domain.entity.Role;
import com.kaychy.auth.domain.enums.RoleName;
import com.kaychy.auth.domain.repository.RoleRepository;
import com.kaychy.auth.domain.entity.User;
import com.kaychy.auth.domain.repository.UserRepository;
import com.kaychy.common.exception.ResourceConflictException;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handles new-account creation. Every self-registered user gets the
 * CUSTOMER role by default; tailor onboarding is a separate, deliberate
 * flow (not implemented yet) rather than a role a customer can pick here.
 */
@Service
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse register(RegisterUserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new ResourceConflictException("An account with this email already exists");
        }

        Role customerRole = roleRepository.findByName(RoleName.CUSTOMER)
                .orElseThrow(() -> new IllegalStateException(
                        "Default role CUSTOMER is not seeded in the database"));

        User user = User.register(
                request.email(),
                passwordEncoder.encode(request.password()),
                request.firstName(),
                request.lastName(),
                request.phoneNumber());
        user.addRole(customerRole);

        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toSet()));
    }
}
