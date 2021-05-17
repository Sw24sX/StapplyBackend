package com.stapply.backend.stapply.service.search;

import com.stapply.backend.stapply.models.AppMain;
import com.stapply.backend.stapply.service.search.servicemodel.AppMarketId;
import com.stapply.backend.stapply.service.search.servicemodel.AppMarketSearch;

import java.util.List;

public interface SearchService {
    List<AppMarketSearch> search(String query, Integer accuracy);
}
