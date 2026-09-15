package com.kaychy.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kaychy.auth.application.dto.RegisterUserRequest;
import com.kaychy.auth.application.dto.UserResponse;
import com.kaychy.auth.application.service.UserRegistrationService;
import com.kaychy.auth.domain.entity.Role;
import com.kaychy.auth.domain.enums.RoleName;
import com.kaychy.auth.domain.repository.RoleRepository;
import com.kaychy.auth.domain.entity.User;
import com.kaychy.auth.domain.repository.UserRepository;
import com.kaychy.common.exception.ResourceConflictException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserRegistrationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserRegistrationService service() {
        return new UserRegistrationService(userRepository, roleRepository, passwordEncoder);
    }

    @Test
    void rejectsRegistrationWhenEmailAlreadyExists() {
        when(userRepository.existsByEmail("jane@example.com")).thenReturn(true);

        RegisterUserRequest request = new RegisterUserRequest(
                "jane@example.com", "password123", "Jane", "Doe", null);

        assertThatThrownBy(() -> service().register(request))
                .isInstanceOf(ResourceConflictException.class);

        verify(userRepository, never()).save(any());
    }

    @Test
    void registersNewUserWithHashedPasswordAndDefaultCustomerRole() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(roleRepository.findByName(RoleName.CUSTOMER)).thenReturn(Optional.of(Role.of(RoleName.CUSTOMER)));
        when(passwordEncoder.encode("password123")).thenReturn("hashed-password");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RegisterUserRequest request = new RegisterUserRequest(
                "jane@example.com", "password123", "Jane", "Doe", "+911234567890");

        UserResponse response = service().register(request);

        assertThat(response.email()).isEqualTo("jane@example.com");
        assertThat(response.firstName()).isEqualTo("Jane");
        assertThat(response.roles()).containsExactly("CUSTOMER");
        verify(passwordEncoder).encode("password123");
    }
}
