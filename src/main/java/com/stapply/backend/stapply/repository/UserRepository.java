package com.stapply.backend.stapply.repository;

import com.stapply.backend.stapply.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
