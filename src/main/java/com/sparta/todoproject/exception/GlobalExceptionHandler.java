package com.sparta.todoproject.exception;

import com.sparta.todoproject.dto.ResponseMsg;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class) // validation 어길 경우 발생하는 예외
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
            builder.append(" | ");
        }

        ResponseMsg<String> responseMsg = ResponseMsg.<String>builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .data(builder.toString())
                .build();


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(responseMsg);
    }

    // 이렇게 해줬는데 사실 커스텀 예외 클래스들의 내용이 거의 같아서 굳이 따로 만들 필요가 있나 하는 생각이 든다... 그냥 Illegal로 다 처리해도 되지 않을까...
    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<ErrorResponse> handlePasswordInvalidException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
        return ResponseEntity
                .status(errorResponse.getStatus())
                .body(errorResponse);
    }

    @ExceptionHandler({DeletedScheduleAccessException.class, ObjectNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleDeletedScheduleAccessException (Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity
                .status(errorResponse.getStatus())
                .body(errorResponse);
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