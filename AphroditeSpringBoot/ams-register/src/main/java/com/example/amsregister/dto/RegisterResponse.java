package com.example.amsregister.dto;

public class RegisterResponse {

    private boolean success;
    private String message;

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
}
