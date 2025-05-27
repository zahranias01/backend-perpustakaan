package com.example.perpustakaan.service;

import com.example.perpustakaan.model.Bahasa;
import com.example.perpustakaan.repository.BahasaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BahasaService {

    @Autowired
    private BahasaRepository repository;

    // 🔸 Mendapatkan semua data Akutansi
    public List<Bahasa> getAllBahasa() {
        return repository.findAll();
    }

    // 🔸 Mendapatkan data Akutansi berdasarkan ID
    public Optional<Bahasa> getBahasaById(int id) {
        return repository.findById(id);
    }

    // 🔸 Menyimpan data Akutansi baru
    public Bahasa saveBahasa(Bahasa bahasa) {
        return repository.save(bahasa);
    }

    // 🔸 Menghapus data Akutansi berdasarkan ID
    public void deleteBahasa(int id) {
        repository.deleteById(id);
    }

    // 🔸 Mencari data Akutansi berdasarkan Title
    public List<Bahasa> searchByTitle(String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }
}
