package com.stapply.backend.stapply.service;

import com.stapply.backend.stapply.models.AppMain;

import java.util.List;

public interface AppMainService {
    List<AppMain> findAll();
    AppMain findById(Long id);
    boolean update(Long id, AppMain app);
    boolean delete(Long id);
    void create(AppMain app);
}
