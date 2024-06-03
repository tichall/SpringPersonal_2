package com.sparta.todoproject.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.todoproject.dto.ResponseMsg;
import com.sparta.todoproject.entity.UserRoleEnum;
import com.sparta.todoproject.statusCode.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
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
    private final long ACCESS_TOKEN_TIME = 24 * 60 * 60 * 1000L; // test 위해 하루로 설정
    private final long REFRESH_TOKEN_TIME = 24 * 60 * 60 * 1000L; // test 위해 하루로 설정

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

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_TIME))
                .signWith(key, signatureAlgorithm)
                .compact();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .claim(AUTHORIZATION_KEY, role)
                        .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_TIME))
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
    public boolean validateToken(String token, HttpServletResponse response){
        try {
            // key를 사용해 올바르게 JWT 검증
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | io.jsonwebtoken.security.SignatureException | MalformedJwtException e) {
            ResponseUtil.setErrorResponse(response, ErrorCode.INVALID_TOKEN_SIGNATURE);
        } catch (ExpiredJwtException e) {
            ResponseUtil.setErrorResponse(response, ErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            ResponseUtil.setErrorResponse(response, ErrorCode.UNSUPPORTED_TOKEN);
        } catch (AuthenticationServiceException e) {
            ResponseUtil.setErrorResponse(response, ErrorCode.LOGIN_FAILED);
        } catch (Exception e) {
            ResponseUtil.setErrorResponse(response, ErrorCode.INVALID_TOKEN);
        }
        return false;
    }

    // 액세스 토큰에서 페이로드값(클레임) 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

}
