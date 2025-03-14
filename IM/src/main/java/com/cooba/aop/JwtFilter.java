package com.cooba.aop;

import com.cooba.constant.FrontEnd;
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
    private final FrontEnd frontEnd;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, @NonNull HttpServletResponse servletResponse, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String path = servletRequest.getRequestURI();
        for (String permitPath : frontEnd.getAllPermitPaths()) {
            if (pathMatcher.match(permitPath, path)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        try {
            jwtHeaderValidator.validHeader(servletRequest.getHeader("Authorization"));
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (JwtValidException e) {
            servletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            servletResponse.getWriter().write(GlobalExceptionHandler.response401Json);
        }
    }
}
