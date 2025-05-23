package com.cooba.config;

import com.cooba.aop.GlobalExceptionHandler;
import com.cooba.aop.JwtFilter;
import com.cooba.constant.FrontEnd;
import com.cooba.constant.RoleEnum;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CsSecurityConfig {
    private final FrontEnd frontEnd;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers(frontEnd.getAllPermitPaths()).permitAll()
                                .requestMatchers("/agent/create").hasAnyRole(RoleEnum.ADMIN.name(), RoleEnum.AGENT.name())
                                .requestMatchers("/agent/**").hasRole(RoleEnum.AGENT.name())
                                .requestMatchers("/customer/create").hasRole(RoleEnum.AGENT.name())
                                .requestMatchers("/channel/create", "/channel/delete").hasRole(RoleEnum.AGENT.name())
                                .anyRequest().authenticated()
                )
                .sessionManagement((sessionManagement) ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer
                                .accessDeniedHandler((request, response, accessDeniedException) -> {
                                    log.error("{}", request.getRequestURI(), accessDeniedException);
                                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                    response.getWriter().write(GlobalExceptionHandler.response401Json);
                                })
                                .authenticationEntryPoint((request, response, authException) -> {
                                    log.error("{}", request.getRequestURI(), authException);
                                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                    response.getWriter().write(GlobalExceptionHandler.response401Json);
                                })
                )
                .build();
    }
}
