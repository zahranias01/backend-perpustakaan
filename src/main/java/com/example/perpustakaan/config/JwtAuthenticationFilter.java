package com.example.perpustakaan.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.perpustakaan.model.JwtUtil;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String path = request.getRequestURI();
        System.out.println("[JwtAuthFilter] PATH: " + path);
        
        // 🔥 Skip JWT check for /auth/**
        if (path.contains("/auth/")) {
            System.out.println("[JwtAuthFilter] Skipping auth filter for path: " + path);
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        System.out.println("[JwtAuthFilter] Authorization header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String npm = null;
        String role = null;

        try {
            npm = jwtUtil.validateTokenAndGetNpm(token);
            role = jwtUtil.getRoleFromToken(token);
            System.out.println("[JwtAuthFilter] Token valid, npm: " + npm + ", role: " + role);
        } catch (Exception e) {
            System.out.println("[JwtAuthFilter] Token invalid: " + e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        if (npm != null && role != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Pastikan role tidak kosong
            if (role.isEmpty()) {
                System.out.println("[JwtAuthFilter] WARNING: role kosong, authentication tidak di-set");
                filterChain.doFilter(request, response);
                return;
            }

            // Pastikan role diawali ROLE_
            if (!role.startsWith("ROLE_")) {
                role = "ROLE_" + role;
            }

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            npm,
                            null,
                            List.of(new SimpleGrantedAuthority(role))
                    );

            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("[JwtAuthFilter] Authentication set for user: " + npm + " with role: " + role);
        } else if (role == null) {
            System.out.println("[JwtAuthFilter] WARNING: Role dari token null, authentication tidak di-set");
        }

        filterChain.doFilter(request, response);
    }
}
