package com.example.perpustakaan.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.perpustakaan.model.JwtUtil;

import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        System.out.println("[JwtAuthFilter] Authorization header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Tidak ada token, langsung lanjutkan
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String npm = null;

        try {
            npm = jwtUtil.validateTokenAndGetNpm(token);
            System.out.println("[JwtAuthFilter] Token valid, npm: " + npm);
        } catch (Exception e) {
            System.out.println("[JwtAuthFilter] Token invalid: " + e.getMessage());
            // ❗ Jangan blokir request → lanjutkan saja
            filterChain.doFilter(request, response);
            return;
        }

        if (npm != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(npm, null, Collections.emptyList());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("[JwtAuthFilter] Authentication set for user: " + npm);
        }

        filterChain.doFilter(request, response);
    }
}
