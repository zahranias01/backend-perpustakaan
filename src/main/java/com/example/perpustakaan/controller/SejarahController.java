package com.example.perpustakaan.controller;

import com.example.perpustakaan.model.Sejarah;
import com.example.perpustakaan.service.SejarahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sejarah")
@CrossOrigin(origins = "http://localhost:5173")
public class SejarahController {

    @Autowired
    private SejarahService service;

    // 🔸 Mendapatkan semua data Akutansi
    @GetMapping("/all")
    public List<Sejarah> getAllSejarah() {
        return service.getAllSejarah();
    }

    // 🔸 Mendapatkan data Akutansi berdasarkan ID
    @GetMapping("/{id}")
    public Optional<Sejarah> getSejarahById(@PathVariable Integer id) {
        return service.getSejarahById(id);
    }

    // 🔸 Menyimpan data Akutansi baru
    @PostMapping("/save")
    public Sejarah saveSejarah(@RequestBody Sejarah sejarah) {
        return service.saveSejarah(sejarah);
    }

    // 🔸 Menghapus data Akutansi berdasarkan ID
    @DeleteMapping("/delete/{id}")
    public String deleteSejarah(@PathVariable Integer id) {
        service.deleteSejarah(id);
        return "Data Sejarah dengan ID " + id + " berhasil dihapus!";
    }

}