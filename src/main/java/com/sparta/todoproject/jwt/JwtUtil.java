package com.sparta.todoproject.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.todoproject.dto.ResponseMsg;
import com.sparta.todoproject.entity.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_KEY = "auth";
    public static final String BEARER_PREFIX = "Bearer ";
    // token 만료 시간 : 60분
    private final long TOKEN_TIME = 60 * 60 * 1000L;

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // 토큰 생성
    public String createToken(String username, UserRoleEnum role) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .claim(AUTHORIZATION_KEY, role)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    // request header에서 JWT 토큰값 가져오기
    public String getTokenFromHeader(HttpServletRequest request) {
        // AUTHORIZATION_HEADER가 달린 헤더값 가져오기
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        // bearerToken 여기에 값이 존재하고, 그 값이 "Bearer "로 시작한다면
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰값 검증
    public boolean validateToken(String token, HttpServletResponse response) {
        try {
            // key를 사용해 올바르게 JWT 검증
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | SignatureException | MalformedJwtException e) {
            setErrorResponse(response, HttpStatus.BAD_REQUEST, "유효하지 않은 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            setErrorResponse(response, HttpStatus.BAD_REQUEST, "만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            setErrorResponse(response, HttpStatus.BAD_REQUEST, "지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            setErrorResponse(response, HttpStatus.BAD_REQUEST, "잘못된 JWT 토큰입니다.");
        }
        return false;
    }

    // 토큰에서 페이로드값(클레임) 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }


    public void setErrorResponse(HttpServletResponse response, HttpStatus status, String msg) {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // MessageBody에 Json 데이터를 보내기 위한 설정
        response.setCharacterEncoding("UTF-8");
        ResponseMsg<Void> responseMsg = ResponseMsg.<Void>builder()
                .statusCode(status.value())
                .message(msg)
                .build();
        try {
            String result = new ObjectMapper().writeValueAsString(responseMsg);
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
