package com.example.perpustakaan.controller;
import com.example.perpustakaan.model.Akutansi;
import com.example.perpustakaan.service.AkutansiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/akutansi")
@CrossOrigin(origins = "http://localhost:5173")
public class AkutansiController {
    @Autowired
    private AkutansiService service;

    // 🔸 Mendapatkan semua data Akutansi
    @GetMapping("/all")
    public List<Akutansi> getAllAkutansi() {
        return service.getAllAkutansi();
    }

    // 🔸 Mendapatkan data Akutansi berdasarkan ID
    @GetMapping("/{id}")
    public Optional<Akutansi> getAkutansiById(@PathVariable Integer id) {
        return service.getAkutansiById(id);
    }

    // 🔸 Menyimpan data Akutansi baru
    @PostMapping("/save")
    public Akutansi saveAkutansi(@RequestBody Akutansi akutansi) {
        return service.saveAkutansi(akutansi);
    }

    // 🔸 Menghapus data Akutansi berdasarkan ID
    @DeleteMapping("/delete/{id}")
    public String deleteAkutansi(@PathVariable Integer id) {
        service.deleteAkutansi(id);
        return "Data Akutansi dengan ID " + id + " berhasil dihapus!";
    }
}
