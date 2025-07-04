package com.example.perpustakaan.repository;

import com.example.perpustakaan.model.Temp;  // Perbaiki import
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Temp, Long> {
    List<Temp> findByBook_BiblioId(Integer biblioId); // Sesuaikan dengan nama field di entitas Book
}

