package com.stapply.backend.stapply.repository;

import com.stapply.backend.stapply.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
