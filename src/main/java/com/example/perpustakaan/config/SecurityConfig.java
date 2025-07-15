package com.example.perpustakaan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.config.http.SessionCreationPolicy; // Tambahkan ini
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.perpustakaan.config.JwtAuthenticationFilter; // kalau filternya di package config
import com.example.perpustakaan.model.JwtUtil; // kalau pakai constructor injection


import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtUtil);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Menonaktifkan CSRF untuk API
           .cors(cors -> cors.configurationSource(corsConfigurationSource()))
           .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/auth/**",
                    "/api/buku/**",
                    "/api/reviews/**",
                    "/api/buku/search",
                    "/api/buku/top-rated",
                    "/api/chat/**",
                    "/api/chatbot/**",
                    "/api/informatika/**",
                    "/api/penerbangan/**",
                    "/api/elektronika/**",
                    "/api/akutansi/**",
                    "/api/manajemen/**",
                    "/api/industri/**",
                    "/api/bahasa/**",
                    "/api/bisnis/**",
                    "/api/filsafat/**",
                    "/api/agama/**",
                    "/api/sejarah/**",
                    "/api/administrasinegara/**"
                ).permitAll()
                .requestMatchers("/api/bookmark/**", "/api/peminjaman/**").authenticated()
                .anyRequest().authenticated()
            )

            .formLogin(form -> form.disable())
            .httpBasic(httpBasic -> httpBasic.disable())
           .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

            return http.build();
        }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
            "http://localhost:5173",
            "https://nurtanio-perpustakaan.netlify.app",
            "http://192.168.100.201:5173"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
