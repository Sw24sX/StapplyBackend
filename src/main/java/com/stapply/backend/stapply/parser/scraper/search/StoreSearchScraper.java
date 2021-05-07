package com.stapply.backend.stapply.parser.scraper.search;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public abstract class StoreSearchScraper {
    protected final String searchBaseUrl;
    private final CloseableHttpClient httpClient;

    public StoreSearchScraper(String searchBaseUrl) {
        this.searchBaseUrl = searchBaseUrl;
        httpClient = HttpClients.createDefault();
    }

    public final List<SearchAppImplInfo> search(String query) throws URISyntaxException, IOException {
        var builder = new URIBuilder(buildSearchUrl(query));
        setQueryParameters(builder, query);

        var response = httpClient.execute(new HttpGet(builder.build()));
        var entity = response.getEntity();
        return parseSearchRequest(EntityUtils.toString(entity));
    }

    public  String buildSearchUrl(String query) {
        return searchBaseUrl;
    }


    public abstract void setQueryParameters(URIBuilder builder, String query);
    public abstract List<SearchAppImplInfo> parseSearchRequest(String responseHTML);
}
