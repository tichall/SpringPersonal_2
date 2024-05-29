package com.sparta.todoproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

//@ResponseStatus(HttpStatus.UNAUTHORIZED)
@Getter
public class PasswordInvalidException extends RuntimeException {
    public PasswordInvalidException(String msg) {
        super(msg);
    }
}
