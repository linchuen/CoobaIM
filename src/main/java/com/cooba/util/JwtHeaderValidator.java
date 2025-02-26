package com.cooba.util;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.cooba.aop.UserThreadLocal;
import com.cooba.dto.UserInfo;
import com.cooba.entity.User;
import com.cooba.exception.JwtValidException;
import com.cooba.repository.UserRepository;
import com.cooba.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtHeaderValidator {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserThreadLocal userThreadLocal;

    public Long validHeader(String authToken) throws JwtValidException {
        if (authToken == null || !authToken.startsWith("Bearer ")) {
            throw new JwtValidException();
        }

        String token = authToken.substring(7);

        try {
            DecodedJWT decodedJWT = jwtUtil.verifyToken(token);
            Long id = decodedJWT.getClaim("id").asLong();
            String name = decodedJWT.getClaim("name").asString();
            String jwtToken = decodedJWT.getToken();

            User user = userRepository.selectById(id);
            if (user == null) throw new UsernameNotFoundException(name);

            UserInfo userInfo = new UserInfo();
            userInfo.setId(id);
            userInfo.setName(name);
            userInfo.setToken(jwtToken);
            userInfo.setOrigin(user);
            userThreadLocal.set(userInfo);

            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return id;
        } catch (Exception e) {
            throw new JwtValidException();
        }
    }
}
