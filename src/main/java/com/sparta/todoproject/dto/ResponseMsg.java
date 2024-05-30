package com.sparta.todoproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@Builder
public class ResponseMsg<T> {
    private int statusCode;
    private String message;
    private T data;

//    public ResponseEntity<ResponseMsg<T>> createSuccessResponseMsg(T data, String message) {
//        ResponseMsg<T> responseMsg = new ResponseMsg<>(HttpStatus.OK.value(), message, data);
//
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(responseMsg);
//    }
}
