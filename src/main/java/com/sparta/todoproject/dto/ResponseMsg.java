package com.sparta.todoproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
// 에러 전달할 때도 T에 void 넣어서 사용할 수 있겠다..
public class ResponseMsg<T> {
    private int statusCode;
    private String message;
    private T data;
}
