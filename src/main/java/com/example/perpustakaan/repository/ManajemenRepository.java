package com.example.perpustakaan.repository;

import com.example.perpustakaan.model.Manajemen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManajemenRepository extends JpaRepository<Manajemen, Integer> {
    List<Manajemen> findByTitleContainingIgnoreCase(String title);
}
