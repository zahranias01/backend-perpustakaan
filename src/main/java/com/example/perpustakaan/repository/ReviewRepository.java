package com.example.perpustakaan.repository;

import com.example.perpustakaan.model.Review;  // Perbaiki import
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBook_BiblioId(Integer biblioId); // Sesuaikan dengan nama field di entitas Book
}

