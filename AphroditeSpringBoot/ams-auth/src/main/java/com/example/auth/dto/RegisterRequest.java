package com.example.auth.dto;

public class RegisterRequest {

    // 这个对应的是 前端的三个 请求
    // 大小写是敏感的
    private String username;
    private String email;
    private String password;

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
