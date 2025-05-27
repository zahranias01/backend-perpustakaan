package com.example.perpustakaan.controller;

import com.example.perpustakaan.model.Bisnis;
import com.example.perpustakaan.service.BisnisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bisnis")
@CrossOrigin(origins = "http://localhost:5173")
public class BisnisController {

    @Autowired
    private BisnisService service;

    // 🔸 Mendapatkan semua data Akutansi
    @GetMapping("/all")
    public List<Bisnis> getAllBisnis() {
        return service.getAllBisnis();
    }

    // 🔸 Mendapatkan data Akutansi berdasarkan ID
    @GetMapping("/{id}")
    public Optional<Bisnis> getBisnisById(@PathVariable Integer id) {
        return service.getBisnisById(id);
    }

    // 🔸 Menyimpan data Akutansi baru
    @PostMapping("/save")
    public Bisnis saveBisnis(@RequestBody Bisnis bisnis) {
        return service.saveBisnis(bisnis);
    }

    // 🔸 Menghapus data Akutansi berdasarkan ID
    @DeleteMapping("/delete/{id}")
    public String deleteBisnis(@PathVariable Integer id) {
        service.deleteBisnis(id);
        return "Data Bisnis dengan ID " + id + " berhasil dihapus!";
    }

}

