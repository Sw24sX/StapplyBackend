package com.stapply.backend.stapply.controller.login.webmodel;

public class TokenWebModel {
    private final String username;
    private final String token;

    public TokenWebModel(String username, String token) {
        this.username = username;
        this.token = token;
    }
}
