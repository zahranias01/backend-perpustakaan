package com.example.perpustakaan.controller;

import com.example.perpustakaan.model.Elektronika;
import com.example.perpustakaan.service.ElektronikaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/elektronika")
@CrossOrigin(origins = "http://localhost:5173")
public class ElektronikaController {

    @Autowired
    private ElektronikaService service;

    // Mendapatkan semua data Informatika
    @GetMapping("/all")
    public List<Elektronika> getAllElektronika() {
        return service.getAllElektronika();
    }

    // Mendapatkan data Informatika berdasarkan ID
    @GetMapping("/{id}")
    public Optional<Elektronika> getElektronikaById(@PathVariable Integer id) {
        return service.getElektronikaById(id);
    }

    // Menyimpan data Informatika baru
    @PostMapping("/save")
    public Elektronika saveElektronika(@RequestBody Elektronika elektronika) {
        return service.saveElektronika(elektronika);
    }

    // Menghapus data Informatika berdasarkan ID
    @DeleteMapping("/delete/{id}")
    public String deleteElektronika(@PathVariable Integer id) {
        service.deleteElektronika(id);
        return "Data Informatika dengan ID " + id + " berhasil dihapus!";
    }
}
