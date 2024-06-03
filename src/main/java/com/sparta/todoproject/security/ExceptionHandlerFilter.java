package com.sparta.todoproject.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.todoproject.dto.ResponseMsg;
import com.sparta.todoproject.jwt.ResponseUtil;
import com.sparta.todoproject.statusCode.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;

/**
 * 필터에서 발생하는 예외 핸들링
 */
//@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        try {
//            filterChain.doFilter(request, response);
//        } catch (SecurityException | SignatureException | MalformedJwtException e) {
//            ResponseUtil.setErrorResponse(response, ErrorCode.INVALID_TOKEN_SIGNATURE);
//        } catch (ExpiredJwtException e) {
//            ResponseUtil.setErrorResponse(response, ErrorCode.EXPIRED_TOKEN);
//        } catch (UnsupportedJwtException e) {
//            ResponseUtil.setErrorResponse(response, ErrorCode.UNSUPPORTED_TOKEN);
//        } catch (AuthenticationServiceException e) {
//            ResponseUtil.setErrorResponse(response, ErrorCode.LOGIN_FAILED);
//        } catch (Exception e) {
//            ResponseUtil.setErrorResponse(response, ErrorCode.INVALID_TOKEN);
//        }
    }
}
