package com.example.perpustakaan.controller;

import com.example.perpustakaan.model.JwtUtil;
import com.example.perpustakaan.model.Login;
import com.example.perpustakaan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class authController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil; // ✅ ini yang sebelumnya hilang

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login loginRequest) {
        Login login = userService.findByNpm(loginRequest.getNpm());

        if (login == null) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "NPM tidak ditemukan");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        if (!userService.checkPassword(loginRequest.getPassword(), login.getPassword())) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Password tidak cocok dengan npm");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        String token = jwtUtil.generateToken(login.getNpm());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login sukses! Nama Pengguna: " + login.getNama());
        response.put("nama", login.getNama());
        response.put("npm", login.getNpm());
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    // Endpoint Registrasi
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Login newUser) {
        if (userService.findByNpm(newUser.getNpm()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NPM sudah terdaftar!");
        }

        userService.saveUser(newUser.getNama(), newUser.getNpm(), newUser.getPassword());
        return ResponseEntity.ok("Registrasi berhasil!!");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Karena JWT stateless, logout cukup di sisi client
        return ResponseEntity.ok("Logout berhasil");
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkLogin(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String npm = jwtUtil.validateTokenAndGetNpm(token);
            if (npm != null) {
                return ResponseEntity.ok("User masih login dengan NPM: " + npm);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token tidak valid atau tidak ada");
    }
}