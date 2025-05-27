package com.example.perpustakaan.service;

import com.example.perpustakaan.model.Akutansi;
import com.example.perpustakaan.repository.AkutansiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AkutansiService {
    @Autowired
    private AkutansiRepository repository;

    // 🔸 Mendapatkan semua data Akutansi
    public List<Akutansi> getAllAkutansi() {
        return repository.findAll();
    }

    // 🔸 Mendapatkan data Akutansi berdasarkan ID
    public Optional<Akutansi> getAkutansiById(int id) {
        return repository.findById(id);
    }

    // 🔸 Menyimpan data Akutansi baru
    public Akutansi saveAkutansi(Akutansi akutansi) {
        return repository.save(akutansi);
    }

    // 🔸 Menghapus data Akutansi berdasarkan ID
    public void deleteAkutansi(int id) {
        repository.deleteById(id);
    }

    // 🔸 Mencari data Akutansi berdasarkan Title
    public List<Akutansi> searchByTitle(String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }
}
