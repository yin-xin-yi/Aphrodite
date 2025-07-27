package com.example.auth.dto;

import lombok.Data;

@Data
public class LoginResponse<T> {

    private Integer code;
    private String message;
    private T data;

    public LoginResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> LoginResponse<T> success(T data) {
        return new LoginResponse<>(200, "操作成功", data);
    }

    public static <T> LoginResponse<T> success() {
        return new LoginResponse<>(200, "操作成功", null);
    }

    public static <T> LoginResponse<T> success(String message, T data) {
        return new LoginResponse<>(200, message, data);
    }

    public static <T> LoginResponse<T> error(Integer code, String message) {
        return new LoginResponse<>(code, message, null);
    }

    public static <T> LoginResponse<T> error(String message) {
        return new LoginResponse<>(500, message, null);
    }

}
