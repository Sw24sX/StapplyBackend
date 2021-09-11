package com.stapply.backend.stapply.parser.scraper.detailed;

import com.stapply.backend.stapply.parser.scraper.AppImpl;
import lombok.var;
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

    public final FullAppImplInfo getDetailedInfo(String addId) throws URISyntaxException, IOException, ParseException {
        var builder = new URIBuilder(buildDetailedInfoUrl(addId));
        setQueryDetailedInfoParameters(builder, addId);

        var response = httpClient.execute(new HttpGet(builder.build()));
        var entity = response.getEntity();
        return parseDetailInfoRequest(EntityUtils.toString(entity), addId);
    }

    public final FullAppImplInfo getDetailedInfo(AppImpl appImpl) throws URISyntaxException, IOException, ParseException {
        return getDetailedInfo(appImpl.id);
    }

    public String buildDetailedInfoUrl(String appId) {
        return appDetailBaseUrl;
    }

    public abstract void setQueryDetailedInfoParameters(URIBuilder builder, String appId);
    public abstract FullAppImplInfo parseDetailInfoRequest(String responseHTML, String appId) throws ParseException;
}
