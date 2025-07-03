package com.example.perpustakaan.repository;

import com.example.perpustakaan.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findByNpm(String npm);

    boolean existsByNpmAndBiblioId(String npm, Integer biblioId);

    Optional<Wishlist> findByNpmAndBiblioId(String npm, Integer biblioId);

    void deleteByNpmAndBiblioId(String npm, Integer biblioId);
}
