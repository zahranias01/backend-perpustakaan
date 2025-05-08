package com.example.perpustakaan.controller;

import com.example.perpustakaan.model.Login;
import com.example.perpustakaan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class authController {

    @Autowired
    private UserService userService;

    // Endpoint Login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Login loginRequest) {
        Login login = userService.findByNpm(loginRequest.getNpm());

        if (login != null && userService.checkPassword(loginRequest.getPassword(), login.getPassword())) {
            return ResponseEntity.ok("Login sukses! Nama Pengguna: " + login.getNama());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login gagal, periksa NPM/password!");
        }
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

    @GetMapping("/test")
    public ResponseEntity<String> testApi() {
        return ResponseEntity.ok("✅ Backend aktif dan aman diakses!");
    }

}   
