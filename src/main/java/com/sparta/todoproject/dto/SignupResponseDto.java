package com.sparta.todoproject.dto;

import com.sparta.todoproject.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SignupResponseDto {
    private Long id;
    private String username;
    private String nickname;
    private LocalDateTime createdAt;

    public SignupResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.createdAt = user.getCreatedAt();
    }
}
