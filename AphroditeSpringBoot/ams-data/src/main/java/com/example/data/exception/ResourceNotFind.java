package com.example.data.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFind extends RuntimeException {

    // 封装一下 自己的异常
    public ResourceNotFind(String message) {
        super(message);
    }
}
