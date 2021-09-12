package com.stapply.backend.stapply.mapper;

import com.stapply.backend.stapply.domain.Store;
import com.stapply.backend.stapply.dto.StoreDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StoreMapper {
    StoreDto storeToStoreDto(Store store);
    List<StoreDto> listStoreToStoreDto(List<Store> stores);

    @Mappings({
            @Mapping(target = "id", ignore = true)
    })
    Store storeDtoToStore(StoreDto storeDto);
}