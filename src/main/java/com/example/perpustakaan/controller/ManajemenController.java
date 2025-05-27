package com.example.perpustakaan.controller;

import com.example.perpustakaan.model.Manajemen;
import com.example.perpustakaan.service.ManajemenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/manajemen")
@CrossOrigin(origins = "http://localhost:5173")
public class ManajemenController {
    @Autowired
    private ManajemenService service;

    // 🔸 Mendapatkan semua data Akutansi
    @GetMapping("/all")
    public List<Manajemen> getAllManajemen() {
        return service.getAllManajemen();
    }

    // 🔸 Mendapatkan data Akutansi berdasarkan ID
    @GetMapping("/{id}")
    public Optional<Manajemen> getManajemenById(@PathVariable Integer id) {
        return service.getManajemenById(id);
    }

    // 🔸 Menyimpan data Akutansi baru
    @PostMapping("/save")
    public Manajemen saveManajemen(@RequestBody Manajemen manajemen) {
        return service.saveManajemen(manajemen);
    }

    // 🔸 Menghapus data Akutansi berdasarkan ID
    @DeleteMapping("/delete/{id}")
    public String deleteManajemen(@PathVariable Integer id) {
        service.deleteManajemen(id);
        return "Data Manajemen dengan ID " + id + " berhasil dihapus!";
    }
}
