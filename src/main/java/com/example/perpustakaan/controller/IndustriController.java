package com.example.perpustakaan.controller;

import com.example.perpustakaan.model.Industri;
import com.example.perpustakaan.service.IndustriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/industri")
@CrossOrigin(origins = "http://localhost:5173")
public class IndustriController {

    @Autowired
    private IndustriService service;

    // 🔸 Mendapatkan semua data Akutansi
    @GetMapping("/all")
    public List<Industri> getAllIndustri() {
        return service.getAllIndustri();
    }

    // 🔸 Mendapatkan data Akutansi berdasarkan ID
    @GetMapping("/{id}")
    public Optional<Industri> getIndustriById(@PathVariable Integer id) {
        return service.getIndustriById(id);
    }

    // 🔸 Menyimpan data Akutansi baru
    @PostMapping("/save")
    public Industri saveIndustri(@RequestBody Industri industri) {
        return service.saveIndustri(industri);
    }

    // 🔸 Menghapus data Akutansi berdasarkan ID
    @DeleteMapping("/delete/{id}")
    public String deleteIndustri(@PathVariable Integer id) {
        service.deleteIndustri(id);
        return "Data Industri dengan ID " + id + " berhasil dihapus!";
    }

}
