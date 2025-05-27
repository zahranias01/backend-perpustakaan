package com.example.perpustakaan.controller;

import com.example.perpustakaan.model.Bahasa;
import com.example.perpustakaan.service.BahasaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bahasa")
@CrossOrigin(origins = "http://localhost:5173")
public class BahasaController {
    @Autowired
    private BahasaService service;

    // 🔸 Mendapatkan semua data Akutansi
    @GetMapping("/all")
    public List<Bahasa> getAllBahasa() {
        return service.getAllBahasa();
    }

    // 🔸 Mendapatkan data Akutansi berdasarkan ID
    @GetMapping("/{id}")
    public Optional<Bahasa> getBahasaById(@PathVariable Integer id) {
        return service.getBahasaById(id);
    }

    // 🔸 Menyimpan data Akutansi baru
    @PostMapping("/save")
    public Bahasa saveBahasa(@RequestBody Bahasa bahasa) {
        return service.saveBahasa(bahasa);
    }

    // 🔸 Menghapus data Akutansi berdasarkan ID
    @DeleteMapping("/delete/{id}")
    public String deleteBahasa(@PathVariable Integer id) {
        service.deleteBahasa(id);
        return "Data Bahasa dengan ID " + id + " berhasil dihapus!";
    }
}
