package com.example.perpustakaan.repository;

import com.example.perpustakaan.model.review;  // Perbaiki import
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<review, Integer> {
    List<review> findByBook_BiblioId(Integer biblioId); // Sesuaikan dengan nama field di entitas Book
}
