package com.example.perpustakaan.service;

import com.example.perpustakaan.model.Elektronika;
import com.example.perpustakaan.repository.ElektronikaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ElektronikaService {

    @Autowired
    private ElektronikaRepository repository;

    // Mendapatkan semua data Elektronika
    public List<Elektronika> getAllElektronika() {
        return repository.findAll();
    }

    // Mendapatkan data Elektronika berdasarkan ID
    public Optional<Elektronika> getElektronikaById(int id) {
        return repository.findById(id);
    }

    // Menyimpan data Elektronika baru
    public Elektronika saveElektronika(Elektronika elektronika) {
        return repository.save(elektronika);
    }

    // Menghapus data Elektronika berdasarkan ID
    public void deleteElektronika(int id) {
        repository.deleteById(id);
    }
}
