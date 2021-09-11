package com.stapply.backend.stapply.service;

import com.stapply.backend.stapply.domain.Store;
import com.stapply.backend.stapply.repository.StoreRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {
    private final StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<Store> getAll() {
        return storeRepository.findAll();
    }

    public Store getById(Long id) {
        return storeRepository.findById(id).orElse(null);
    }

    public Store create(Store store) {
        return storeRepository.save(store);
    }

    public Store update(Long id, Store store) {
        Store storeBd = getById(id);
        BeanUtils.copyProperties(store, storeBd, "id");
        return storeRepository.save(storeBd);
    }

    public void delete(Long id) {
        Store store = getById(id);
        storeRepository.delete(store);
    }
}
