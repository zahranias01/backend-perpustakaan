package com.example.perpustakaan.service;

import com.example.perpustakaan.model.Filsafat;
import com.example.perpustakaan.repository.FilsafatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilsafatService {

    @Autowired
    private FilsafatRepository repository;

    // 🔸 Mendapatkan semua data Akutansi
    public List<Filsafat> getAllFilsafat() {
        return repository.findAll();
    }

    // 🔸 Mendapatkan data Akutansi berdasarkan ID
    public Optional<Filsafat> getFilsafatById(int id) {
        return repository.findById(id);
    }

    // 🔸 Menyimpan data Akutansi baru
    public Filsafat saveFilsafat(Filsafat filsafat) {
        return repository.save(filsafat);
    }

    // 🔸 Menghapus data Akutansi berdasarkan ID
    public void deleteFilsafat(int id) {
        repository.deleteById(id);
    }

    // 🔸 Mencari data Akutansi berdasarkan Title
    public List<Filsafat> searchByTitle(String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }
}