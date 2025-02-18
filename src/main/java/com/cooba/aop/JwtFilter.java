package com.cooba.aop;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.cooba.dto.UserInfo;
import com.cooba.entity.User;
import com.cooba.service.UserService;
import com.cooba.util.JwtUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final UserThreadLocal userThreadLocal;

    public static final String[] ALL_PERMIT_PATHS = {"/user/register", "/user/login", "/swagger-ui/**", "/v3/api-docs/**","/ws/**"};
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, @NonNull HttpServletResponse servletResponse, @NonNull FilterChain filterChain) throws ServletException, IOException {

        String path = servletRequest.getRequestURI();
        for (String permitPath : ALL_PERMIT_PATHS) {
            if (pathMatcher.match(permitPath, path)) {
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

            User user = userService.getInfo(id);
            if (user == null) throw new UsernameNotFoundException(name);

            UserInfo userInfo = new UserInfo();
            userInfo.setId(id);
            userInfo.setName(name);
            userInfo.setToken(jwtToken);
            userInfo.setOrigin(user);
            userThreadLocal.set(userInfo);

            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            servletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            servletResponse.getWriter().write(GlobalExceptionHandler.response401Json);
        }
    }
}
