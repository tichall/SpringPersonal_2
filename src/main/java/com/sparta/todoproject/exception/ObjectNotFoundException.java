package com.sparta.todoproject.exception;

import lombok.Getter;

@Getter
public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String msg) {
        super(msg);
    }
}