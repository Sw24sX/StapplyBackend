package com.stapply.backend.stapply.parser.scraper.review;

import com.stapply.backend.stapply.parser.scraper.AppImpl;
import lombok.var;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;

public abstract class ReviewsScraper {

    protected final String appDetailBaseUrl;
    private final CloseableHttpClient httpClient;


    public ReviewsScraper(String appDetailBaseUrl) {
        this.appDetailBaseUrl = appDetailBaseUrl;
        httpClient = HttpClients.createDefault();
    }

    public final List<Review> getComments(AppImpl appImpl) throws URISyntaxException, IOException {
        var request = new HttpPost(buildCommentUrl(appImpl));
        setQueryCommentsParameters(request, appImpl);

        var response = httpClient.execute(request);
        var entity = response.getEntity();
        return parseDetailCommentsRequest(EntityUtils.toString(entity));
    }

    public String buildCommentUrl(AppImpl searchAppImplInfo) {
        return appDetailBaseUrl;
    }

    public abstract void setQueryCommentsParameters(HttpPost builder, AppImpl searchAppImplInfo) throws UnsupportedEncodingException;
    public abstract List<Review> parseDetailCommentsRequest(String responseHTML);
}
