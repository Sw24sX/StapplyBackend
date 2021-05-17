package com.stapply.backend.stapply.parser;

public class DefaultDetailUrl {
    public static final String googlePlay = "https://play.google.com/store/apps/details?id=%s&hl=ru&gl=US";
    public static final String appStore = "https://apps.apple.com/ru/app/%s";

    public static String createGooglePlayUrl(String appId) {
        return String.format(googlePlay, appId);
    }

    public static String createAppStoreUrl(String appId) {
        return String.format(appStore, appId);
    }
}
