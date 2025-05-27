package com.example.perpustakaan.service;

import com.example.perpustakaan.model.Penerbangan;
import com.example.perpustakaan.repository.PenerbanganRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PenerbanganService {

    @Autowired
    private PenerbanganRepository repository;

    // Mendapatkan semua data Penerbangan
    public List<Penerbangan> getAllPenerbangan() {
        return repository.findAll();
    }

    // Mendapatkan data Penerbangan berdasarkan ID
    public Optional<Penerbangan> getPenerbanganById(Integer id) {
        return repository.findById(id);
    }

    // Menyimpan data Penerbangan
    public Penerbangan savePenerbangan(Penerbangan penerbangan) {
        return repository.save(penerbangan);
    }

    // Menghapus data Penerbangan berdasarkan ID
    public void deletePenerbangan(Integer id) {
        repository.deleteById(id);
    }
}
