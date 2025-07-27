package com.example.auth.dto;

public class Data {

    private String token;
    private String username;
    private Long userid;

    public Data(String token, String username, Long userid) {
        this.token = token;
        this.userid = userid;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public Long getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }
}
