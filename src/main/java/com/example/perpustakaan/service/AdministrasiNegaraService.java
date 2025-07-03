package com.example.perpustakaan.service;
import com.example.perpustakaan.model.AdministrasiNegara;
import com.example.perpustakaan.repository.AdministrasiNegaraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministrasiNegaraService {

    @Autowired
    private AdministrasiNegaraRepository repository;

    // 🔸 Mendapatkan semua data Amneg
    public List<AdministrasiNegara> getAllAdministrasiNegara() {
        return repository.findAll();
    }

    // 🔸 Mendapatkan data Amneg berdasarkan ID
    public Optional<AdministrasiNegara> getAdministrasiNegaraById(int id) {
        return repository.findById(id);
    }

    // 🔸 Menyimpan data Akutansi baru
    public AdministrasiNegara saveAdministrasiNegara(AdministrasiNegara administrasinegara) {
        return repository.save(administrasinegara);
    }

    // 🔸 Menghapus data Amneg berdasarkan ID
    public void deleteAdministrasiNegara(int id) {
        repository.deleteById(id);
    }

    // 🔸 Mencari data Amneg berdasarkan Title
    public List<AdministrasiNegara> searchByTitle(String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }
}
