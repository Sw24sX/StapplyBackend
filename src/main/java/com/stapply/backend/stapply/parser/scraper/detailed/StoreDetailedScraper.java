package com.stapply.backend.stapply.parser.scraper.detailed;

import com.stapply.backend.stapply.parser.scraper.AppImpl;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

public abstract class StoreDetailedScraper {

    protected final String appDetailBaseUrl;
    private final CloseableHttpClient httpClient;

    public StoreDetailedScraper(String appDetailBaseUrl) {
        this.appDetailBaseUrl = appDetailBaseUrl;
        httpClient = HttpClients.createDefault();
    }

    public final FullAppImplInfo getDetailedInfo(AppImpl appImpl) throws URISyntaxException, IOException, ParseException {
        var builder = new URIBuilder(buildDetailedInfoUrl(appImpl));
        setQueryDetailedInfoParameters(builder, appImpl);

        var response = httpClient.execute(new HttpGet(builder.build()));
        var entity = response.getEntity();
        return parseDetailInfoRequest(EntityUtils.toString(entity), appImpl);
    }

    public String buildDetailedInfoUrl(AppImpl appImpl) {
        return appDetailBaseUrl;
    }

    public abstract void setQueryDetailedInfoParameters(URIBuilder builder, AppImpl appImpl);
    public abstract FullAppImplInfo parseDetailInfoRequest(String responseHTML, AppImpl appImpl) throws ParseException;
}
