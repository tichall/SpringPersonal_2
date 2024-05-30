package com.sparta.todoproject.controller;

import com.sparta.todoproject.dto.ResponseMsg;
import com.sparta.todoproject.dto.SignupRequestDto;
import com.sparta.todoproject.dto.SignupResponseDto;
import com.sparta.todoproject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<ResponseMsg<SignupResponseDto>> signup(@RequestBody @Valid SignupRequestDto requestDto) {
        return userService.signup(requestDto);
    }
}
