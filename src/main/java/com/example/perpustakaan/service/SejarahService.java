package com.example.perpustakaan.service;

import com.example.perpustakaan.model.Sejarah;
import com.example.perpustakaan.repository.SejarahRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SejarahService {

    @Autowired
    private SejarahRepository repository;

    // 🔸 Mendapatkan semua data Akutansi
    public List<Sejarah> getAllSejarah() {
        return repository.findAll();
    }

    // 🔸 Mendapatkan data Akutansi berdasarkan ID
    public Optional<Sejarah> getSejarahById(int id) {
        return repository.findById(id);
    }

    // 🔸 Menyimpan data Akutansi baru
    public Sejarah saveSejarah(Sejarah sejarah) {
        return repository.save(sejarah);
    }

    // 🔸 Menghapus data Akutansi berdasarkan ID
    public void deleteSejarah(int id) {
        repository.deleteById(id);
    }

    // 🔸 Mencari data Akutansi berdasarkan Title
    public List<Sejarah> searchByTitle(String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }
}
