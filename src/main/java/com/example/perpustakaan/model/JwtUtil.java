package com.example.perpustakaan.model;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private final Key secretKey = Keys.hmacShaKeyFor("IniRahasiaSangatPanjangMinimal32Karakter".getBytes());
    private final long expirationMillis = 1000 * 60 * 60 * 10; // 10 jam

    // 🔑 Generate token dengan role
    public String generateToken(String npm, String role) {
        return Jwts.builder()
                .setSubject(npm)
                .addClaims(Map.of("role", role.replace("ROLE_", ""))) // claim tambahan
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // 🔑 Validate token dan ambil npm
    public String validateTokenAndGetNpm(String token) {
        try {
            Claims claims = parseClaims(token);
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

    // 🔑 Ambil role dari token
    public String getRoleFromToken(String token) {
        Claims claims = parseClaims(token);
        return claims.get("role", String.class);
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
