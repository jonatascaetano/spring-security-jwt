package com.jonatas.teste_security.services;

import java.time.Duration;
import java.time.Instant;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class TokenService {
    
    public String generateAccessToken(String username) {
        // Implementação para gerar token JWT
        Algorithm algorithm = Algorithm.HMAC256("Bananas de pijamas");
        String token = JWT.create()
            .withIssuer("teste_security")
            .withSubject(username)
            .withExpiresAt(Instant.now().plus(Duration.ofHours(24)))
            .sign(algorithm);
        return token;
    }

    // Gera um refresh token JWT
    public String generateRefreshToken(String username) {
        Algorithm algorithm = Algorithm.HMAC256("Bananas de pijamas");
        return JWT.create()
            .withIssuer("teste_security")
            .withSubject(username)
            .withExpiresAt(Instant.now().plus(Duration.ofDays(90)))
            .sign(algorithm);
    }

    public String validatAccessTokenn(String token) {
        Algorithm algorithm = Algorithm.HMAC256("Bananas de pijamas");
        return JWT.require(algorithm)
        .withIssuer("teste_security")
        .build()
        .verify(token)
        .getSubject();
    }

    // Valida o refresh token JWT
    public String validateRefreshToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256("Bananas de pijamas");
        return JWT.require(algorithm)
            .withIssuer("teste_security")
            .build()
            .verify(token)
            .getSubject();
    }
}
