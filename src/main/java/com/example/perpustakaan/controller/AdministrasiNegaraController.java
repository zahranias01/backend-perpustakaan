package com.example.perpustakaan.controller;
import com.example.perpustakaan.model.AdministrasiNegara;
import com.example.perpustakaan.service.AdministrasiNegaraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/administrasinegara")
@CrossOrigin(origins = "http://localhost:5173")
public class AdministrasiNegaraController {

    @Autowired
    private AdministrasiNegaraService service;

    // 🔸 Mendapatkan semua data Amneg
    @GetMapping("/all")
    public List<AdministrasiNegara> getAllAdministrasiNegara() {
        return service.getAllAdministrasiNegara();
    }

    // 🔸 Mendapatkan data Amneg berdasarkan ID
    @GetMapping("/{id}")
    public Optional<AdministrasiNegara> getAdministrasiNegaraById(@PathVariable Integer id) {
        return service.getAdministrasiNegaraById(id);
    }

    // 🔸 Menyimpan data Amneg baru
    @PostMapping("/save")
    public AdministrasiNegara saveAdministrasiNegara(@RequestBody AdministrasiNegara administrasiNegara) {
        return service.saveAdministrasiNegara(administrasiNegara);
    }

    // 🔸 Menghapus data Amneg berdasarkan ID
    @DeleteMapping("/delete/{id}")
    public String deleteAdministrasiNegara(@PathVariable Integer id) {
        service.deleteAdministrasiNegara(id);
        return "Data AdministrasiNegara dengan ID " + id + " berhasil dihapus!";
    }
}
