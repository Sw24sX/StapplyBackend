package com.stapply.backend.stapply.repository;

import com.stapply.backend.stapply.models.AppMain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRepository extends JpaRepository<AppMain, Long> {
}
