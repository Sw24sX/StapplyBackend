package com.stapply.backend.stapply.repository;

import com.stapply.backend.stapply.domain.AppMain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppRepository extends JpaRepository<AppMain, Long> {
    AppMain findByGooglePlayId(String googlePlayId);

    boolean existsByGooglePlayId(String googlePlayId);
    boolean existsByAppStoreId(String appStoreId);

    boolean existsByGooglePlayIdOrAppStoreIdOrAppGalleryId(String googlePlayId, String appStoreId, String appGalleryId);

    AppMain findByGooglePlayIdOrAppStoreIdOrAppGalleryId(String googlePlayId, String appStoreId, String appGalleryId);
}
