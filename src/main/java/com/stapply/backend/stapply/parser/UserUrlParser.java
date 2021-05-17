package com.stapply.backend.stapply.parser;

import java.util.HashMap;

public class UserUrlParser {
    public static HashMap<String, String> parseGooglePlayUrl(String url) throws Exception {
        var result = new HashMap<String, String>();
        result.put("url", url);
        var baseAndParams = url.split("\\?");
        if(baseAndParams.length != 2)
            throw new Exception();
        result.put("base", baseAndParams[0]);
        var params = baseAndParams[1].split("&");
        for(var param : params) {
            var nameAndValue = param.split("=");
            if(nameAndValue.length != 2)
                throw new Exception();
            result.put(nameAndValue[0], nameAndValue[1]);
        }
        return result;
    }

    public static boolean googlePlayUrlIsValid(HashMap<String, String> url) {
        var example = "https://play.google.com/store/apps/details";
        var validId = url.containsKey("id") && url.containsKey("base");
        var validBase = url.get("base").equals(example);
        return validBase && validId;
    }

    public static String getIdFromAppStoreUrl(String url) throws Exception {
        var splitUrl = url.split("/");
        if(splitUrl.length < 1)
            throw new Exception();

        var id = splitUrl[splitUrl.length - 1];
        if(!id.startsWith("id"))
            throw new Exception();
        return id;
    }
}
