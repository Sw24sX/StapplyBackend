package com.stapply.backend.stapply.parser.appstore;


import com.stapply.backend.stapply.parser.scraper.search.SearchAppImplInfo;
import com.stapply.backend.stapply.parser.scraper.search.StoreSearchScraper;
import org.apache.http.client.utils.URIBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class AppStoreSearchScraper extends StoreSearchScraper {

    public AppStoreSearchScraper(String searchBaseUrl) {
        super(searchBaseUrl);
    }

    public AppStoreSearchScraper() {
        super("https://www.apple.com/ru/search/");
    }

    @Override
    public String buildSearchUrl(String query) {
        return super.searchBaseUrl + query;
    }

    @Override
    public void setQueryParameters(URIBuilder builder, String query) {
        builder.setParameter("src", "serp");
    }

    @Override
    public List<SearchAppImplInfo> parseSearchRequest(String responseHTML) {
        var doc = Jsoup.parse(responseHTML);
        var appElements = doc.getElementsByClass("as-explore-curated");
        var result = new ArrayList<SearchAppImplInfo>();
        for(var element : appElements.first().children()) {
            var imageSrc = getImage(element);
            var name = getName(element);
            var description = getDescription(element);
            var id = getId(element);
            var app = new SearchAppImplInfo(imageSrc, name, id);
            app.otherParameters.put("description", description);
            result.add(app);
        }
        return result;
    }

    private String getId(Element element) {
        var moreInfo = element.getElementsByClass("as-links-name more").attr("href");
        var startIndex = 0;
        for(var i = moreInfo.length() - 1; i >= 0; i--){
            if(moreInfo.charAt(i) == '/')
            {
                startIndex = i + 1;
                break;
            }
        }

        return moreInfo.substring(startIndex);
    }

    private String getImage(Element element) {
        return element.getElementsByClass("ir as-explore-img").first().attr("src");
    }

    private String getName(Element element) {
        return element.getElementsByClass("as-productname as-util-relatedlink").first().text();
    }

    private String getDescription(Element element) {
        return element.getElementsByClass("as-productdescription").text();
    }
}
