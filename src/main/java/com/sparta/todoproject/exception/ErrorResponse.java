package com.sparta.todoproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {
    private HttpStatus status;
    private int statusCode;
    private String errMsg;

    public ErrorResponse(HttpStatus status, String errMsg) {
        this.status = status;
        this.statusCode = status.value();
        this.errMsg = errMsg;
    }
}
