package com.cooba.aop;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.cooba.dto.UserBasicInfo;
import com.cooba.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.security.SignatureException;

@WebFilter
@RequiredArgsConstructor
public class JwtFilter implements Filter {
    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 获取 Authorization Header
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing or invalid Authorization header.");
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
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Failed to process JWT token.");
        }
    }
}
