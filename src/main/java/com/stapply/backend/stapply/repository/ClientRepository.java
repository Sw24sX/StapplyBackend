package com.stapply.backend.stapply.repository;

import com.stapply.backend.stapply.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
