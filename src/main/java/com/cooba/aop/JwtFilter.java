package com.cooba.aop;

import com.cooba.exception.JwtValidException;
import com.cooba.util.JwtHeaderValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtHeaderValidator jwtHeaderValidator;

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

        try {
            jwtHeaderValidator.validHeader(servletRequest);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (JwtValidException e) {
            servletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            servletResponse.getWriter().write(GlobalExceptionHandler.response401Json);
        }
    }
}
