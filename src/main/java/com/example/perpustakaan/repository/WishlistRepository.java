package com.example.perpustakaan.repository;

import com.example.perpustakaan.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByNpm(String npm);
    boolean existsByNpmAndBiblioId(String npm, Integer biblio_id);
}
