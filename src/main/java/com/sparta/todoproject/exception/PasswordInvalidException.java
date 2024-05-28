package com.sparta.todoproject.exception;

import lombok.Getter;

//@ResponseStatus(HttpStatus.UNAUTHORIZED)
@Getter
public class PasswordInvalidException extends RuntimeException {
    private ErrorCode errorCode;
    public PasswordInvalidException(String msg, ErrorCode errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }
}
