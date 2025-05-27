package com.example.perpustakaan.controller;

import com.example.perpustakaan.model.Informatika;
import com.example.perpustakaan.service.InformatikaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/informatika")
@CrossOrigin(origins = "http://localhost:5173")
public class InformatikaController {

    @Autowired
    private InformatikaService service;

    // Mendapatkan semua data Informatika
    @GetMapping("/all")
    public List<Informatika> getAllInformatika() {
        return service.getAllInformatika();
    }

    // Mendapatkan data Informatika berdasarkan ID
    @GetMapping("/{id}")
    public Optional<Informatika> getInformatikaById(@PathVariable Integer id) {
        return service.getInformatikaById(id);
    }

    // Menyimpan data Informatika baru
    @PostMapping("/save")
    public Informatika saveInformatika(@RequestBody Informatika informatika) {
        return service.saveInformatika(informatika);
    }

    // Menghapus data Informatika berdasarkan ID
    @DeleteMapping("/delete/{id}")
    public String deleteInformatika(@PathVariable Integer id) {
        service.deleteInformatika(id);
        return "Data Informatika dengan ID " + id + " berhasil dihapus!";
    }
}
