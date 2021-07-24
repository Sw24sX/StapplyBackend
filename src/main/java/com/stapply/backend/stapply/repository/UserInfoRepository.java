package com.stapply.backend.stapply.repository;

import com.stapply.backend.stapply.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
}
