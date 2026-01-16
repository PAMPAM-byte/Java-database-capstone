package com.project.back_end.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    // 1 hour expiration (you can change, but keep it present)
    private static final long EXPIRATION_MS = 60 * 60 * 1000;

    // REQUIRED: method to return signing key
    private Key getSigningKey() {
        // Generates a secure random key for HS256
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    // REQUIRED: generate JWT using Jwts.builder(), issuedAt, expiration
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
}
