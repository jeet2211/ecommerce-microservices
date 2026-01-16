package com.ecommerce.user.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtil {
    private final SecretKey key;

    private final long jwtExpirationMs;

    public JwtUtil(@Value("${app.jwt.secret}") String secret, @Value("${app.jwt.expiration-ms}") long jwtSecretMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.jwtExpirationMs = jwtSecretMs;
    }

    public String generateToken(Long userId, String email, String role) {
        Long now = System.currentTimeMillis();
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("email", email)
                .claim("role", role)
                .issuedAt(new Date(now))
                .expiration(new Date(jwtExpirationMs)).signWith(key)
                .compact();
    }

    public Jws<Claims> validateToken(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token);

    }

    public Long getUserIdFromToken(String token) {
        Claims claims = validateToken(token).getBody();
        return Long.valueOf(claims.getSubject());

    }

}
