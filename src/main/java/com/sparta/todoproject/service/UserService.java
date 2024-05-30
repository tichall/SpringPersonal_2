package com.sparta.todoproject.service;

import com.sparta.todoproject.dto.ResponseMsg;
import com.sparta.todoproject.dto.SignupRequestDto;
import com.sparta.todoproject.dto.SignupResponseDto;
import com.sparta.todoproject.entity.User;
import com.sparta.todoproject.entity.UserRoleEnum;
import com.sparta.todoproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final String ADMIN_TOKEN = "aAJOFkdfnaoADSCADAsdfawsjtdrkAIxckl";

    @Transactional
    public ResponseEntity<ResponseMsg<SignupResponseDto>> signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String nickname = requestDto.getNickname();
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> checkUsername = userRepository.findByUsername(username);

        if(checkUsername.isPresent()) {
            throw new IllegalArgumentException("이미 가입된 사용자 이름입니다.");
        }

        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 토큰이 일치하지 않습니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, nickname, password, role);
        userRepository.save(user);

        ResponseMsg<SignupResponseDto> responseMsg = ResponseMsg.<SignupResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("유저 회원가입 완료")
                .data(new SignupResponseDto(user))
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseMsg);
    }



}
