package com.example.auth.dto;

public class Data {

    private String token;
    private String username;
    private Long userid;
    private int code;

    public Data(String token, String username, Long userid, int code) {
        this.token = token;
        this.userid = userid;
        this.username = username;
        this.code = code;
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
