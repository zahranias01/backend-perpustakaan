package com.example.perpustakaan.controller;
import com.example.perpustakaan.model.Agama;
import com.example.perpustakaan.service.AgamaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/agama")
@CrossOrigin(origins = "http://localhost:5173")
public class AgamaController {

    @Autowired
    private AgamaService service;

    // 🔸 Mendapatkan semua data Akutansi
    @GetMapping("/all")
    public List<Agama> getAllAgama() {
        return service.getAllAgama();
    }

    // 🔸 Mendapatkan data Akutansi berdasarkan ID
    @GetMapping("/{id}")
    public ResponseEntity<Agama> getAgamaById(@PathVariable Integer id) {
        Optional<Agama> agama = service.getAgamaById(id);
        return agama.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }


    // 🔸 Menyimpan data Akutansi baru
    @PostMapping("/save")
    public Agama saveAgama(@RequestBody Agama agama) {
        return service.saveAgama(agama);
    }

    // 🔸 Menghapus data Akutansi berdasarkan ID
    @DeleteMapping("/delete/{id}")
    public String deleteAgama(@PathVariable Integer id) {
        service.deleteAgama(id);
        return "Data Agama dengan ID " + id + " berhasil dihapus!";
    }

}
