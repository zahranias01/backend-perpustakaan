package com.example.perpustakaan.controller;

import com.example.perpustakaan.model.Penerbangan;
import com.example.perpustakaan.service.PenerbanganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/penerbangan")
@CrossOrigin(origins = "http://localhost:5173")
public class PenerbanganController {

    @Autowired
    private PenerbanganService service;

    // Mendapatkan semua data Penerbangan
    @GetMapping("/all")
    public List<Penerbangan> getAllPenerbangan() {
        return service.getAllPenerbangan();
    }

    // Mendapatkan data Penerbangan berdasarkan ID
    @GetMapping("/{id}")
    public Optional<Penerbangan> getPenerbanganById(@PathVariable Integer id) {
        return service.getPenerbanganById(id);
    }

    // Menyimpan data Penerbangan baru
    @PostMapping("/save")
    public Penerbangan savePenerbangan(@RequestBody Penerbangan penerbangan) {
        return service.savePenerbangan(penerbangan);
    }

    // Menghapus data Penerbangan berdasarkan ID
    @DeleteMapping("/delete/{id}")
    public String deletePenerbangan(@PathVariable Integer id) {
        service.deletePenerbangan(id);
        return "Data Penerbangan dengan ID " + id + " berhasil dihapus!";
    }

}
