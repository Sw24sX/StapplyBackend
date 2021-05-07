package com.stapply.backend.stapply.parser.googleplay;

import com.stapply.backend.stapply.parser.scraper.AppImpl;
import com.stapply.backend.stapply.parser.scraper.detailed.FullAppImplInfo;
import com.stapply.backend.stapply.parser.scraper.detailed.StoreDetailedScraper;
import org.apache.http.client.utils.URIBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class GooglePlayDetailInfoScraper extends StoreDetailedScraper {
    public GooglePlayDetailInfoScraper(String appDetailBaseUrl) {
        super(appDetailBaseUrl);
    }

    public GooglePlayDetailInfoScraper() {
        super("https://play.google.com/store/apps/details");
    }

    @Override
    public void setQueryDetailedInfoParameters(URIBuilder builder, AppImpl appImpl) {
        builder.setParameter("id", appImpl.id);
        builder.setParameter("hl", "ru");
        builder.setParameter("gl", "ru");
    }

    @Override
    public FullAppImplInfo parseDetailInfoRequest(String responseHTML, AppImpl appImpl) throws ParseException {
        var document = Jsoup.parse(responseHTML);
        var developer = document.getElementsByClass("R8zArc").first().text();
        var scoreStr = document.getElementsByClass("BHMmbe").first().text();
        var score = (Double)NumberFormat.getNumberInstance().parse(scoreStr);
        var images = findImages(document);
        var description = findDescription(document);
        return new FullAppImplInfo(appImpl, developer, score, description, images);
    }

    private List<String> findImages(Document document) {
        var images = new ArrayList<String>();
        for (Element element :document.getElementsByClass("T75of DYfLw")) {
            var src = element.attr("src");
            if (src.equals(""))
                src = element.attr("data-src");
            images.add(src);
        }
        return images;
    }

    private String findDescription(Document document) {
        return document.getElementsByClass("DWPxHb").first().text();
    }
}
