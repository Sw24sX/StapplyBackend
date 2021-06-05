package com.stapply.backend.stapply.repository;

import com.stapply.backend.stapply.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
