package com.example.perpustakaan.controller;

import com.example.perpustakaan.model.Filsafat;
import com.example.perpustakaan.service.FilsafatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/filsafat")
@CrossOrigin(origins = "http://localhost:5173")
public class FilsafatController {

    @Autowired
    private FilsafatService service;

    // 🔸 Mendapatkan semua data Akutansi
    @GetMapping("/all")
    public List<Filsafat> getAllFilsafat() {
        return service.getAllFilsafat();
    }

    // 🔸 Mendapatkan data Akutansi berdasarkan ID
    @GetMapping("/{id}")
    public Optional<Filsafat> getFilsafatById(@PathVariable Integer id) {
        return service.getFilsafatById(id);
    }

    // 🔸 Menyimpan data Akutansi baru
    @PostMapping("/save")
    public Filsafat saveFilsafat(@RequestBody Filsafat filsafat ) {
        return service.saveFilsafat(filsafat);
    }

    // 🔸 Menghapus data Akutansi berdasarkan ID
    @DeleteMapping("/delete/{id}")
    public String deleteFilsafat(@PathVariable Integer id) {
        service.deleteFilsafat(id);
        return "Data Filsafat dengan ID " + id + " berhasil dihapus!";
    }

}