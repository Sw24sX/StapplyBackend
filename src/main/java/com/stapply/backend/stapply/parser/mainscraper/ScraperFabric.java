package com.stapply.backend.stapply.parser.mainscraper;

import com.stapply.backend.stapply.parser.appstore.AppStoreDetailedInfoScraper;
import com.stapply.backend.stapply.parser.appstore.AppStoreReviewScraper;
import com.stapply.backend.stapply.parser.appstore.AppStoreSearchScraper;
import com.stapply.backend.stapply.parser.googleplay.GooglePlayDetailInfoScraper;
import com.stapply.backend.stapply.parser.googleplay.GooglePlayReviewsScraper;
import com.stapply.backend.stapply.parser.googleplay.GooglePlaySearchScraper;
import com.stapply.backend.stapply.parser.scraper.StoreScraper;
import lombok.var;

public class ScraperFabric {
    private static StoreScraper googlePlay = null;
    private static StoreScraper appStoreScraper = null;

    public static StoreScraper GooglePlayScraper() {
        if (googlePlay != null)
            return googlePlay;

        var search = new GooglePlaySearchScraper();
        var detail = new GooglePlayDetailInfoScraper();
        var reviews = new GooglePlayReviewsScraper();
        var result = new StoreScraper(search, detail, reviews);
        googlePlay = result;
        return result;
    }

    public static StoreScraper AppStoreScraper() {
        if(appStoreScraper != null)
            return appStoreScraper;

        var search = new AppStoreSearchScraper();
        var detail = new AppStoreDetailedInfoScraper();
        var reviews = new AppStoreReviewScraper();

        var result = new StoreScraper(search, detail, reviews);
        appStoreScraper = result;
        return result;
    }
}
