package com.example.perpustakaan.service;

import com.example.perpustakaan.model.Manajemen;
import com.example.perpustakaan.repository.ManajemenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManajemenService {

    @Autowired
    private ManajemenRepository repository;

    // 🔸 Mendapatkan semua data Akutansi
    public List<Manajemen> getAllManajemen() {
        return repository.findAll();
    }

    // 🔸 Mendapatkan data Akutansi berdasarkan ID
    public Optional<Manajemen> getManajemenById(int id) {
        return repository.findById(id);
    }

    // 🔸 Menyimpan data Akutansi baru
    public Manajemen saveManajemen(Manajemen manajemen) {
        return repository.save(manajemen);
    }

    // 🔸 Menghapus data Akutansi berdasarkan ID
    public void deleteManajemen(int id) {
        repository.deleteById(id);
    }

    // 🔸 Mencari data Akutansi berdasarkan Title
    public List<Manajemen> searchByTitle(String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }
}
