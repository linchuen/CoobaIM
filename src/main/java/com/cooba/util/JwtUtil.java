package com.cooba.util;

import com.auth0.jwt.JWT;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cooba.constant.JwtSecret;
import com.cooba.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {
    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private final long ttl;

    public JwtUtil(JwtSecret jwtSecret) {
        this.algorithm = Algorithm.HMAC256(jwtSecret.getDefaultValue());
        this.verifier = JWT.require(algorithm).withIssuer("Cooba").build();
        this.ttl = jwtSecret.getTtlSecond();
    }

    public String createToken(User user, LocalDateTime issueTime) {
        return createToken(user, null, issueTime);
    }

    public String createToken(User user, Algorithm algorithm, LocalDateTime issueTime) {
        return JWT.create()
                .withIssuer("Cooba")
                .withSubject("User Token")
                .withClaim("id", user.getId())
                .withClaim("name", user.getName())
                .withIssuedAt(issueTime.atZone(ZoneOffset.systemDefault()).toInstant())
                .withExpiresAt(issueTime.plusSeconds(ttl).atZone(ZoneOffset.systemDefault()).toInstant())
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm == null ? this.algorithm : algorithm);
    }

    public DecodedJWT verifyToken(String jwtToken) {
        return verifyToken(jwtToken, null);
    }

    public DecodedJWT verifyToken(String jwtToken, Algorithm algorithm) {
        if (algorithm == null) {
            return this.verifier.verify(jwtToken);
        } else {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("Cooba")
                    .build();

            return verifier.verify(jwtToken);
        }
    }

    public static void main(String[] args) {
        JwtSecret secret = new JwtSecret();
        JwtUtil jwtUtil = new JwtUtil(secret);

        User user = new User();
        user.setId(123L);
        user.setName("Aiden");
        String token = jwtUtil.createToken(user, LocalDateTime.now());
        System.out.println(token);

        DecodedJWT decodedJWT = jwtUtil.verifyToken(token);
        System.out.println(decodedJWT.getClaim("id"));
    }
}
