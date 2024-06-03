package com.sparta.todoproject.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.todoproject.dto.LoginRequestDto;
import com.sparta.todoproject.dto.LoginResponseDto;
import com.sparta.todoproject.entity.UserRoleEnum;
import com.sparta.todoproject.jwt.JwtTokenHelper;
import com.sparta.todoproject.util.ResponseUtil;
import com.sparta.todoproject.statusCode.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtTokenHelper jwtTokenHelper;
    public JwtAuthenticationFilter(JwtTokenHelper jwtTokenHelper) {
        this.jwtTokenHelper = jwtTokenHelper;
        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword(), null)
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    // 로그인 성공 시 토큰 생성 후 헤더에 추가
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        String username = userDetails.getUsername();
        UserRoleEnum role = userDetails.getUser().getRole();

        String token = jwtTokenHelper.createToken(username, role);
        response.addHeader(JwtTokenHelper.AUTHORIZATION_HEADER, token);
        ResponseUtil.setSuccessResponse(response, new LoginResponseDto(userDetails), "로그인 성공");
    }

    // 로그인 실패 시
    // AuthenticationFailureHandler
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.error("로그인 실패");

        ResponseUtil.setErrorResponse(response, ErrorCode.LOGIN_FAILED);
    }
}
