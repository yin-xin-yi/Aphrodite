package com.example.amsregister.dto;

public class RegisterResponse {

    // ————————————————————————————————————————————————————————————————————
    // 私有成员
    private boolean success;
    private String message;
    private String token;
    // ————————————————————————————————————————————————————————————————————

    // ————————————————————————————————————————————————————————————————————
    // 公有成员
    public RegisterResponse(boolean success, String message, String token) {
        this.success = success; // boolen
        this.message = message; // 字符串
        this.token = token;
    }

    // 这个用于 注册不成功的时候
    public RegisterResponse(boolean success, String message) {
        this.success = success; // boolen
        this.message = message; // 字符串
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    // ————————————————————————————————————————————————————————————————————
}
