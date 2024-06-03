package com.sparta.todoproject.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

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
