package com.sparta.todoproject.security;

import com.sparta.todoproject.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 토큰 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String servletPath = request.getServletPath(); //
        // 리프레쉬 요청 시 액세스 토큰 검증 X..?
        if (servletPath.equals("/api/refresh")) {
            filterChain.doFilter(request, response);
        }

        String token = jwtUtil.getTokenFromHeader(request); // 헤더에서 토큰값만 가져오기

        if (StringUtils.hasText(token)) { // 토큰값이 존재하면
            try {
                jwtUtil.validateToken(token, request); // 토큰값 유효한지 아닌지 검사..
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            Claims info = jwtUtil.getUserInfoFromToken(token);

            try {
                setAuthentication(info.getSubject()); // 이거 실행하고 나서 어떻게 되는 거지
            } catch (Exception e) {
                log.error(e.getMessage());
                return; // 필터 체인 중단
            }
        }

        log.info("Token이 존재하지 않습니다.");
        filterChain.doFilter(request, response);
    }

    // 인증 처리
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    // Authentication 객체 생성
    private Authentication createAuthentication(String username) {
        // 해당하는 user 객체 정보를 userDetails 객체에 반환 (username, password, authority)
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
