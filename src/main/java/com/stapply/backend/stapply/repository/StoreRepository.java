package com.stapply.backend.stapply.repository;

import com.stapply.backend.stapply.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
