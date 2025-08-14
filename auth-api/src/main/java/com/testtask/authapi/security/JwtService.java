package com.testtask.authapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {
    private final Key key;
    private final long ttlMinutes;

    public JwtService(@Value("${security.jwt.secret}") String secret,
                      @Value("${security.jwt.ttl-minutes}") long ttlMinutes) {
        if (secret == null || secret.getBytes(StandardCharsets.UTF_8).length < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 32 bytes");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.ttlMinutes = ttlMinutes;
    }

    public String generateToken(UUID userId) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(ttlMinutes, ChronoUnit.MINUTES)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public PrincipalUser parseAndValidate(String token) {
        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        UUID userId = UUID.fromString(jws.getBody().getSubject());
        return new PrincipalUser(userId);
    }
}
