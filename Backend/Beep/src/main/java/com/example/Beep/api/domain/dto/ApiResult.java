package com.example.Beep.api.domain.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResult<T> {
    private HttpStatus status;
    private T data;

    public ApiResult(T data, HttpStatus status) {
        this.data = data;
        this.status = status;
    }
}
