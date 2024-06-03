package com.sparta.todoproject.statusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    REQUEST_SUCCESS(HttpStatus.OK),
    CREATED_SUCCESS(HttpStatus.CREATED);

    private HttpStatus status;
}
