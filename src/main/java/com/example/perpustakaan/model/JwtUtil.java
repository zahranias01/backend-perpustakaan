package com.example.perpustakaan.model;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key secretKey = Keys.hmacShaKeyFor("IniRahasiaSangatPanjangMinimal32Karakter".getBytes());

    private final long expirationMillis = 1000 * 60 * 60 * 10; // 10 jam

    public String generateToken(String npm) {
        return Jwts.builder()
                .setSubject(npm)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String validateTokenAndGetNpm(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            if (claims.getExpiration().before(new Date())) {
                System.out.println("[JwtUtil] Token expired");
                return null;
            }

            System.out.println("[JwtUtil] Token valid for npm: " + claims.getSubject());
            return claims.getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("[JwtUtil] Token invalid: " + e.getMessage());
            return null;
        }
    }
}