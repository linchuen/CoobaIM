package com.cooba.aop;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.cooba.dto.UserBasicInfo;
import com.cooba.util.JwtUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public static final String[] ALL_PERMIT_PATHS = {"/user/register", "/user/login"};

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, @NonNull HttpServletResponse servletResponse, @NonNull FilterChain filterChain) throws ServletException, IOException {

        String path = servletRequest.getRequestURI();
        for (String permitPath: ALL_PERMIT_PATHS) {
            if (path.startsWith(permitPath)){
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        // 获取 Authorization Header
        String authHeader = servletRequest.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            servletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            servletResponse.getWriter().write(GlobalExceptionHandler.response401Json);
            return;
        }

        String token = authHeader.substring(7); // 去掉 "Bearer " 前缀

        try {
            // 解析和验证 JWT 令牌
            DecodedJWT decodedJWT = jwtUtil.verifyToken(token);
            Long id = decodedJWT.getClaim("id").asLong();
            String name = decodedJWT.getClaim("name").asString();
            String jwtToken = decodedJWT.getToken();

            UserBasicInfo userBasicInfo = new UserBasicInfo();
            userBasicInfo.setId(id);
            userBasicInfo.setName(name);
            userBasicInfo.setToken(jwtToken);
            UserThreadLocal.set(userBasicInfo);

            // 继续过滤链
            filterChain.doFilter(servletRequest, servletResponse);

        } catch (Exception e) {
            servletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            servletResponse.getWriter().write(GlobalExceptionHandler.response401Json);
        }
    }
}
