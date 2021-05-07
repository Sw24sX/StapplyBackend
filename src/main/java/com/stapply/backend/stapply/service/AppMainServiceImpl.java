package com.stapply.backend.stapply.service;

import com.stapply.backend.stapply.models.AppMain;
import com.stapply.backend.stapply.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppMainServiceImpl implements AppMainService{
    @Autowired
    private AppRepository appRepository;

    @Override
    public List<AppMain> findAll() {
        return appRepository.findAll();
    }
}
