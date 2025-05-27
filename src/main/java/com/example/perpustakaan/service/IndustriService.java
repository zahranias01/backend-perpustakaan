package com.example.perpustakaan.service;

import com.example.perpustakaan.model.Industri;
import com.example.perpustakaan.repository.IndustriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IndustriService {

    @Autowired
    private IndustriRepository repository;

    // 🔸 Mendapatkan semua data Akutansi
    public List<Industri> getAllIndustri() {
        return repository.findAll();
    }

    // 🔸 Mendapatkan data Akutansi berdasarkan ID
    public Optional<Industri> getIndustriById(int id) {
        return repository.findById(id);
    }

    // 🔸 Menyimpan data Akutansi baru
    public Industri saveIndustri(Industri industri) {
        return repository.save(industri);
    }

    // 🔸 Menghapus data Akutansi berdasarkan ID
    public void deleteIndustri(int id) {
        repository.deleteById(id);
    }

    // 🔸 Mencari data Akutansi berdasarkan Title
    public List<Industri> searchByTitle(String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }
}
