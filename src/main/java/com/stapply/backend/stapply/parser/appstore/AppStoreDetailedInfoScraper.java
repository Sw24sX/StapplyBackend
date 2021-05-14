package com.stapply.backend.stapply.parser.appstore;

import com.stapply.backend.stapply.parser.scraper.AppImpl;
import com.stapply.backend.stapply.parser.scraper.detailed.FullAppImplInfo;
import com.stapply.backend.stapply.parser.scraper.detailed.StoreDetailedScraper;
import org.apache.http.client.utils.URIBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class AppStoreDetailedInfoScraper extends StoreDetailedScraper {
    public AppStoreDetailedInfoScraper(String appDetailBaseUrl) {
        super(appDetailBaseUrl);
    }

    @Override
    public void setQueryDetailedInfoParameters(URIBuilder builder, String appId) {

    }

    @Override
    public FullAppImplInfo parseDetailInfoRequest(String responseHTML, String appId) throws ParseException {
        var document = Jsoup.parse(responseHTML);
        //var description = getDescription(document); //todo
        var description = "";
        var score = getScore(document);
        var images = getImages(document);
        var developer = getDeveloper(document);
        var name = getName(document);
        var imageLogo = getImageLogoSrc(document);
        return new FullAppImplInfo(appId, name, imageLogo, developer, score, description, images);
    }

    public AppStoreDetailedInfoScraper() {
        super("https://apps.apple.com/ru/app/");
    }

    public String buildDetailedInfoUrl(String appId) {
        return appDetailBaseUrl + appId;
    }

    private String getImageLogoSrc(Document doc) {
        return doc.getElementsByClass("we-artwork__source")
                .first()
                .attr("srcset");
    }

    private String getName(Document doc) {
        return doc.getElementsByClass("product-header__title app-header__title")
                .first()
                .text();
    }

    private String getDeveloper(Document doc) {
        return doc.getElementsByClass("product-header__identity app-header__identity")
                .first()
                .children()
                .first()
                .text();
    }

    private String getDescription(Document doc) {
        return doc.getElementsByClass("section__description").first().text();
    }

    private Double getScore(Document doc) throws ParseException {
        var scoreStr = doc.getElementsByClass("we-customer-ratings__averages__display").first().text();
        return Double.parseDouble(scoreStr);
    }

    private List<String> getImages(Document doc) {
        var result = new ArrayList<String>();
        var screenshots = doc.getElementsByClass("l-row l-row--peek we-screenshot-viewer__screenshots-list").first();
        for(var element : screenshots.children()) {
            var src = element.getElementsByClass("we-artwork__source").first().attr("srcset");
            result.add(src);
        }
        return result;
    }
}
