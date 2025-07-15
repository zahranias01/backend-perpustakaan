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
        String authHeader = request.getHeader("Authorization");
        System.out.println("[JwtAuthFilter] Authorization header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // ❗Tidak ada token → lanjutkan ke filter berikutnya
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String npm;

        try {
            npm = jwtUtil.validateTokenAndGetNpm(token);
            System.out.println("[JwtAuthFilter] Token valid, npm: " + npm);
        } catch (Exception e) {
            System.out.println("[JwtAuthFilter] Token invalid: " + e.getMessage());
            // ❗Jangan set authentication jika token tidak valid
            filterChain.doFilter(request, response);
            return;
        }

        if (npm != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            npm,
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_USER")) // 🔷 Tambahkan authority minimal
                    );

            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("[JwtAuthFilter] Authentication set for user: " + npm);
        }

        filterChain.doFilter(request, response);
    }
}