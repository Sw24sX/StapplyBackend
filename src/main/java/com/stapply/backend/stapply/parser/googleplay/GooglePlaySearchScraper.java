package com.stapply.backend.stapply.parser.googleplay;


import com.stapply.backend.stapply.parser.scraper.search.SearchAppImplInfo;
import com.stapply.backend.stapply.parser.scraper.search.StoreSearchScraper;
import lombok.var;
import org.apache.http.client.utils.URIBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class GooglePlaySearchScraper extends StoreSearchScraper {

    public GooglePlaySearchScraper(String searchUrl) {
        super(searchUrl);
    }

    public GooglePlaySearchScraper() {
        super("https://play.google.com/store/search");
    }

    @Override
    public void setQueryParameters(URIBuilder builder, String query) {
        builder.setParameter("q", query)
                .setParameter("c", "apps")
                .setParameter("hl", "ru")
                .setParameter("gl", "US");
    }

    @Override
    public List<SearchAppImplInfo> parseSearchRequest(String responseHTML) {
        var result = new ArrayList<SearchAppImplInfo>();
        var doc = Jsoup.parse(responseHTML);
        for (Element el : doc.body().getElementsByClass("Vpfmgd")){
            result.add(parseApp(el));
        }

        return result;
    }

    private SearchAppImplInfo parseApp(Element element) {
        var app = new SearchAppImplInfo(getImageSrc(element), getName(element), getId(element)); //todo
        app.otherParameters.put("developer", getDeveloper(element));
        app.otherParameters.put("score", getScore(element).toString());
        return app;
    }

    private String getImageSrc (Element element) {
        return element.getElementsByClass("QNCnCf").first().attr("data-src");
    }

    private String getName (Element element) {
        return element.getElementsByClass("nnK0zc").first().text();
    }

    private String getId (Element element) {
        //todo
        var url = element.getElementsByClass("JC71ub").first().attr("href");
        return url.substring(23);
    }

    private String getDeveloper (Element element) {
        return element.getElementsByClass("KoLSrc").first().text();
    }

    private Integer getScore (Element element) {
        return element.getElementsByClass("vQHuPe bUWb7c").size();
    }
}
