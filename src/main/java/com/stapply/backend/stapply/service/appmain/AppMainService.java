package com.stapply.backend.stapply.service.appmain;

import com.stapply.backend.stapply.models.AppMain;
import com.stapply.backend.stapply.service.appmain.servicemodels.AppLinks;

import java.util.List;

public interface AppMainService {
    List<AppMain> findAll();
    AppMain findById(Long id);
    AppMain findByMarketId(String googlePlayId, String appStoreId, String appGalleryId);
    boolean existByMarketId(String googlePlayId, String appStoreId, String appGalleryId);
    boolean update(Long id, AppMain app);
    boolean delete(Long id);
    AppMain create(AppMain app);
    AppMain create(AppLinks app);

    AppMain findByGooglePlayId(String googlePlayId);
    boolean existByGooglePlayId(String googlePlayId);
}
