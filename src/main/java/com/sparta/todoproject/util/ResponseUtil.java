package com.sparta.todoproject.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.todoproject.dto.LoginResponseDto;
import com.sparta.todoproject.dto.ResponseMsg;
import com.sparta.todoproject.statusCode.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

public class ResponseUtil {

    public static void setSuccessResponse(HttpServletResponse response, LoginResponseDto responseDto, String msg) {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // MessageBody에 Json 데이터를 보내기 위한 설정
        response.setCharacterEncoding("UTF-8");
        ResponseMsg<LoginResponseDto> responseMsg = ResponseMsg.<LoginResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message(msg)
                .data(responseDto)
                .build();
        try {
            String result = new ObjectMapper().writeValueAsString(responseMsg); // json 형태의 데이터를 String으로 변환
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) {
        response.setStatus(errorCode.getStatusCode());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // MessageBody에 Json 데이터를 보내기 위한 설정
        response.setCharacterEncoding("UTF-8");
        ResponseMsg<Void> responseMsg = ResponseMsg.<Void>builder()
                .statusCode(errorCode.getStatusCode())
                .message(errorCode.getMessage())
                .build();
        try {
            String result = new ObjectMapper().writeValueAsString(responseMsg); // json 형태의 데이터를 String으로 변환
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
