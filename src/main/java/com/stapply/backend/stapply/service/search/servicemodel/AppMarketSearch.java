package com.stapply.backend.stapply.service.search.servicemodel;

import com.stapply.backend.stapply.controller.search.webmodel.SearchApp;
import com.stapply.backend.stapply.parser.DefaultDetailUrl;
import com.stapply.backend.stapply.parser.scraper.search.SearchAppImplInfo;

import java.util.Arrays;

public class AppMarketSearch {
    private Long id;
    private String name;
    private String nameLower;
    private String developer;
    private String avatarSrc;
    private String linkAppStore;
    private String linkAppGallery;
    private String linkGooglePlay;
    private boolean isTracking = false;


    public Boolean getTracking() {
        return isTracking;
    }

    public void setTracking(boolean tracking) {
        isTracking = tracking;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.nameLower = name.toLowerCase();
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getAvatarSrc() {
        return avatarSrc;
    }

    public void setAvatarSrc(String avatarSrc) {
        this.avatarSrc = avatarSrc;
    }

    public String getLinkAppStore() {
        return linkAppStore;
    }

    public void setLinkAppStore(String linkAppStore) {
        this.linkAppStore = linkAppStore;
    }

    public String getLinkAppGallery() {
        return linkAppGallery;
    }

    public void setLinkAppGallery(String linkAppGallery) {
        this.linkAppGallery = linkAppGallery;
    }

    public String getLinkGooglePlay() {
        return linkGooglePlay;
    }

    public void setLinkGooglePlay(String linkGooglePlay) {
        this.linkGooglePlay = linkGooglePlay;
    }

    public int calculate(String otherName) {
        otherName = otherName.toLowerCase();
        int[][]dp = new int[nameLower.length() + 1][otherName.length() + 1];

        for (int i = 0; i <= nameLower.length(); i++) {
            for (int j = 0; j <= otherName.length(); j++) {
                if (i == 0) {
                    dp[i][j]= j;
                }
                else if (j == 0) {
                    dp[i][j]= i;
                }
                else {
                    dp[i][j]= min(dp[i - 1][j - 1]+ costOfSubstitution(nameLower.charAt(i - 1), otherName.charAt(j - 1)),
                            dp[i - 1][j]+ 1,
                            dp[i][j - 1]+ 1);
                }
            }
        }

        return dp[nameLower.length()][otherName.length()];
    }

    private static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    private static int min(int... numbers) {
        return Arrays.stream(numbers)
                .min().orElse(Integer.MAX_VALUE);
    }

    public void putDataFromParsedSearchApp(SearchAppImplInfo appSearchData, Long id) {
        if(checkPropertyData(name))
        {
            name = appSearchData.name;
            nameLower = name.toLowerCase();
        }

        if(checkPropertyData(avatarSrc))
            avatarSrc = appSearchData.imageSrc;

        if(checkPropertyData(developer) && appSearchData.otherParameters.containsKey("developer"))
            developer = appSearchData.otherParameters.get("developer");

        this.id = id;
    }

    private static boolean checkPropertyData(String value) {
        return value == null || value.isEmpty();
    }

    public void setLinkAppStore(SearchAppImplInfo appSearchData) throws Exception {
        if(appSearchData.id == null)
            throw new Exception();
        this.linkAppStore = DefaultDetailUrl.createAppStoreUrl(appSearchData.id);
    }

    public void setLinkGooglePlay(SearchAppImplInfo appSearchData) throws Exception {
        if(appSearchData.id == null)
            throw new Exception();
        this.linkGooglePlay = DefaultDetailUrl.createGooglePlayUrl(appSearchData.id);
    }

    public void setLinkAppGallery(SearchAppImplInfo appSearchData) {
        this.linkAppGallery = linkAppGallery;
    }


}
