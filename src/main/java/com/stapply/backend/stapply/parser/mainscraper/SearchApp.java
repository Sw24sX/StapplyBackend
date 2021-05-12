package com.stapply.backend.stapply.parser.mainscraper;

import com.stapply.backend.stapply.parser.scraper.search.SearchAppImplInfo;

import java.util.Arrays;

public class SearchApp {
    private Integer id;
    private String name;
    private String nameLower;
    private String developer;
    private String avatarSrc;
    private String linkAppStore;
    private String linkAppGallery;
    private String linkGooglePlay;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public static SearchApp fromGoogleApp(SearchAppImplInfo gp, Integer id) {
        var app = new SearchApp();
        app.setName(gp.name);
        app.setAvatarSrc(gp.imageSrc);
        var developer = gp.otherParameters.get("developer");
        app.setDeveloper(developer);
        var srcGoogle = String.format("https://play.google.com/store/apps/details?id=%s&hl=ru&gl=US", gp.id);
        app.setLinkGooglePlay(srcGoogle);
        app.setId(id);
        return app;
    }
}
