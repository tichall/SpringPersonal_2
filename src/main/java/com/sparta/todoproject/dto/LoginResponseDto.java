package com.sparta.todoproject.dto;

import com.sparta.todoproject.security.UserDetailsImpl;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String username;
    private String nickname;

    public LoginResponseDto(UserDetailsImpl userDetails) {
        this.username = userDetails.getUsername();
        this.nickname = userDetails.getUser().getNickname();
    }
}
