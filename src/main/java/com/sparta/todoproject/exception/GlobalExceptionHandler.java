package com.sparta.todoproject.exception;

import com.sparta.todoproject.dto.ResponseMsg;
import com.sparta.todoproject.statusCode.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseMsg<String>> handleValidationException(MethodArgumentNotValidException e) {
        // validation 어기면 BindingResult에 오류 관련 내용이 담김
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder
                    .append("[")
                    .append(fieldError.getField())
                    .append("] ")
                    .append(fieldError.getDefaultMessage())
                    .append(" => 입력된 값 : [")
                    .append(fieldError.getRejectedValue())
                    .append("]");
            builder.append(System.lineSeparator());
        }

        ResponseMsg<String> responseMsg = ResponseMsg.<String>builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("올바르지 않은 입력이 있습니다.")
                .data(builder.toString())
                .build();


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(responseMsg);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ResponseMsg<Void>> handleObjectNotFoundException (Exception e) {
        ResponseMsg<Void> responseMsg = ResponseMsg.<Void>builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(responseMsg);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseMsg<Void>> handleIllegalArgumentException(Exception e) {

        ResponseMsg<Void> responseMsg = ResponseMsg.<Void>builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(responseMsg);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMsg<Void>> handleException(Exception e) {

        ResponseMsg<Void> responseMsg = ResponseMsg.<Void>builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(responseMsg);
    }
}