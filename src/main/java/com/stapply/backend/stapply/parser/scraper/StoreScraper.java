package com.stapply.backend.stapply.parser.scraper;

import com.stapply.backend.stapply.parser.scraper.detailed.FullAppImplInfo;
import com.stapply.backend.stapply.parser.scraper.detailed.StoreDetailedScraper;
import com.stapply.backend.stapply.parser.scraper.review.IReviewScraper;
import com.stapply.backend.stapply.parser.scraper.review.Review;
import com.stapply.backend.stapply.parser.scraper.search.SearchAppImplInfo;
import com.stapply.backend.stapply.parser.scraper.search.StoreSearchScraper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

public class StoreScraper {
    private final StoreSearchScraper searchScraper;
    private final StoreDetailedScraper detailedScraper;
    private final IReviewScraper reviewScraper;

    public StoreScraper(StoreSearchScraper searchScraper, StoreDetailedScraper detailedScraper,
                        IReviewScraper reviewScraper) {
        this.searchScraper = searchScraper;
        this.detailedScraper = detailedScraper;
        this.reviewScraper = reviewScraper;
    }

    public List<SearchAppImplInfo> search (String query) throws IOException, URISyntaxException {
        return searchScraper.search(query);
    }

    public FullAppImplInfo detailed (AppImpl appImpl) throws ParseException, IOException, URISyntaxException {
        return detailedScraper.getDetailedInfo(appImpl);
    }

    public FullAppImplInfo detailed (String id) throws ParseException, IOException, URISyntaxException {
        return detailedScraper.getDetailedInfo(id);
    }

    public List<Review> reviews (AppImpl appImpl, Integer count) throws InterruptedException, IOException, URISyntaxException {
        return reviewScraper.getComments(appImpl, 0);
    }

    public List<Review> reviews (AppImpl appImpl) throws InterruptedException, IOException, URISyntaxException {
        return reviewScraper.getComments(appImpl, 0);
    }
}
