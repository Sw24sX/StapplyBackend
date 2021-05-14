package com.stapply.backend.stapply.service;

import com.stapply.backend.stapply.models.AppMain;
import com.stapply.backend.stapply.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppMainServiceImpl implements AppMainService{
    private final AppRepository appRepository;

    @Autowired
    public AppMainServiceImpl(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    @Override
    public List<AppMain> findAll() {
        return appRepository.findAll();
    }

    @Override
    public AppMain findById(Long id) {
        if(!appRepository.existsById(id))
            return null;
        return appRepository.findById(id).get();
    }

    @Override
    public AppMain findByMarketId(String googlePlayId, String appStoreId, String appGalleryId) {
        googlePlayId = googlePlayId == null ? "" : googlePlayId;
        appStoreId = appStoreId == null ? "" : appStoreId;
        appGalleryId = appGalleryId == null ? "" : appGalleryId;
        if(!appRepository.existsByGooglePlayIdOrAppStoreIdOrAppGalleryId(googlePlayId, appStoreId, appGalleryId))
            return null;
        return appRepository.findByGooglePlayIdOrAppStoreIdOrAppGalleryId(googlePlayId, appStoreId, appGalleryId);
    }

    @Override
    public boolean existByMarketId(String googlePlayId, String appStoreId, String appGalleryId) {
        googlePlayId = googlePlayId == null ? "" : googlePlayId;
        appStoreId = appStoreId == null ? "" : appStoreId;
        appGalleryId = appGalleryId == null ? "" : appGalleryId;
        return appRepository.existsByGooglePlayIdOrAppStoreIdOrAppGalleryId(googlePlayId, appStoreId, appGalleryId);
    }

    @Override
    public boolean update(Long id, AppMain app) {
        if(!appRepository.existsById(id))
            return false;
        app.setId(id);
        appRepository.save(app);
        return true;
    }

    @Override
    public boolean delete(Long id) {
        if(!appRepository.existsById(id))
            return false;
        appRepository.deleteById(id);
        return true;
    }

    @Override
    public void create(AppMain app) {
        appRepository.save(app);
    }

    @Override
    public AppMain findByGooglePlayId(String googlePlayId) {
        return appRepository.findByGooglePlayId(googlePlayId);
    }

    @Override
    public boolean existByGooglePlayId(String googlePlayId) {
        return appRepository.existsByGooglePlayId(googlePlayId);
    }
}
