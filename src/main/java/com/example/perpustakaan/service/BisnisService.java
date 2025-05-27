package com.example.perpustakaan.service;

import com.example.perpustakaan.model.Bisnis;
import com.example.perpustakaan.repository.BisnisRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BisnisService {

    @Autowired
    private BisnisRepository repository;

    // 🔸 Mendapatkan semua data Akutansi
    public List<Bisnis> getAllBisnis() {
        return repository.findAll();
    }

    // 🔸 Mendapatkan data Akutansi berdasarkan ID
    public Optional<Bisnis> getBisnisById(int id) {
        return repository.findById(id);
    }

    // 🔸 Menyimpan data Akutansi baru
    public Bisnis saveBisnis(Bisnis bisnis) {
        return repository.save(bisnis);
    }

    // 🔸 Menghapus data Akutansi berdasarkan ID
    public void deleteBisnis(int id) {
        repository.deleteById(id);
    }

    // 🔸 Mencari data Akutansi berdasarkan Title
    public List<Bisnis> searchByTitle(String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }
}
