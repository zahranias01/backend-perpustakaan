package com.example.perpustakaan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.perpustakaan.repository.BookRepository;
import com.example.perpustakaan.repository.PeminjamanRepository;
import com.example.perpustakaan.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "http://localhost:5173")
public class DashboardController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PeminjamanRepository peminjamanRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/stats")
    public Map<String, Long> getDashboardStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("books", bookRepository.count());
        stats.put("loans", peminjamanRepository.count());
        stats.put("users", userRepository.count());
        return stats;
    }
}

