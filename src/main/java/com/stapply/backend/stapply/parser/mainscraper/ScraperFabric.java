package com.stapply.backend.stapply.parser.mainscraper;

import com.stapply.backend.stapply.parser.appstore.AppStoreDetailedInfoScraper;
import com.stapply.backend.stapply.parser.appstore.AppStoreReviewScraper;
import com.stapply.backend.stapply.parser.appstore.AppStoreSearchScraper;
import com.stapply.backend.stapply.parser.googleplay.GooglePlayDetailInfoScraper;
import com.stapply.backend.stapply.parser.googleplay.GooglePlayReviewsScraper;
import com.stapply.backend.stapply.parser.googleplay.GooglePlaySearchScraper;
import com.stapply.backend.stapply.parser.scraper.StoreScraper;

public class ScraperFabric {
    public static StoreScraper GooglePlayScraper() {
        var search = new GooglePlaySearchScraper();
        var detail = new GooglePlayDetailInfoScraper();
        var reviews = new GooglePlayReviewsScraper();

        return new StoreScraper(search, detail, reviews);
    }

    public static StoreScraper AppStoreScraper() {
        var search = new AppStoreSearchScraper();
        var detail = new AppStoreDetailedInfoScraper();
        var reviews = new AppStoreReviewScraper();

        return new StoreScraper(search, detail, reviews);
    }
}
