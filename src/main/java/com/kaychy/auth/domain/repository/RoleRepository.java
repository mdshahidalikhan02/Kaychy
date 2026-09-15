package com.kaychy.auth.domain.repository;

import java.util.Optional;

import com.kaychy.auth.domain.entity.Role;
import com.kaychy.auth.domain.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}
