package com.stapply.backend.stapply.controller.login.webmodel;

public class TokenWebModel {
    public final String username;
    public final String token;

    public TokenWebModel(String username, String token) {
        this.username = username;
        this.token = token;
    }
}
