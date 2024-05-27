package com.sparta.todoproject.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private int status;
    private String errMsg;

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.errMsg = errorCode.getMsg();
    }
}
