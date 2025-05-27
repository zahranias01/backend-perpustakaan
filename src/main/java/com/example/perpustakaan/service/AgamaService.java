package com.example.perpustakaan.service;

import com.example.perpustakaan.model.Agama;
import com.example.perpustakaan.repository.AgamaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgamaService {

    @Autowired
    private AgamaRepository repository;

    // 🔸 Mendapatkan semua data Akutansi
    public List<Agama> getAllAgama() {
        return repository.findAll();
    }

    // 🔸 Mendapatkan data Akutansi berdasarkan ID
    public Optional<Agama> getAgamaById(int id) {
        return repository.findById(id);
    }

    // 🔸 Menyimpan data Akutansi baru
    public Agama saveAgama(Agama agama) {
        return repository.save(agama);
    }

    // 🔸 Menghapus data Akutansi berdasarkan ID
    public void deleteAgama(int id) {
        repository.deleteById(id);
    }

    // 🔸 Mencari data Akutansi berdasarkan Title
    public List<Agama> searchByTitle(String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }
}
