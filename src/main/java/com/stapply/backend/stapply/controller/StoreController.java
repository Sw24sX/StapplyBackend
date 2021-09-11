package com.stapply.backend.stapply.controller;

import com.stapply.backend.stapply.domain.Store;
import com.stapply.backend.stapply.dto.StoreDto;
import com.stapply.backend.stapply.mapper.StoreMapper;
import com.stapply.backend.stapply.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("store")
public class StoreController {
    private final StoreMapper storeMapper;
    private final StoreService storeService;

    @Autowired
    public StoreController(StoreMapper storeMapper, StoreService storeService) {
        this.storeMapper = storeMapper;
        this.storeService = storeService;
    }

    @GetMapping
    public List<StoreDto> getAll() {
        return storeMapper.listStoreToStoreDto(storeService.getAll());
    }

    @GetMapping("{id}")
    public StoreDto getById(@PathVariable("id") Long id) {
        return storeMapper.storeToStoreDto(storeService.getById(id));
    }

    @PostMapping
    public StoreDto create(@RequestBody StoreDto storeDto) {
        Store result = storeService.create(storeMapper.storeDtoToStore(storeDto));
        return storeMapper.storeToStoreDto(result);
    }

    @PutMapping("{id}")
    public StoreDto update(@RequestBody StoreDto storeDto, @PathVariable("id") Long id) {
        Store result = storeService.update(id, storeMapper.storeDtoToStore(storeDto));
        return storeMapper.storeToStoreDto(result);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        storeService.delete(id);
    }
}
