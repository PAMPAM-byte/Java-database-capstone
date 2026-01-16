package com.project.back_end.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    // 1 hour token validity (you can change)
    private static final long EXPIRATION_MS = 60 * 60 * 1000;

    // Signing key for JWT
    private final Key signingKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // REQUIRED: method that returns a signing key
    public Key getSigningKey() {
        return signingKey;
    }

    // REQUIRED: generates a JWT using Jwts.builder(), with issuedAt and expiration
    public String generateToken(String username) {
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + EXPIRATION_MS);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    // Optional helper (nice for your controllers/services)
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
