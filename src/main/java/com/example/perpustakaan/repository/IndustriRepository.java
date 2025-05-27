package com.example.perpustakaan.repository;

import com.example.perpustakaan.model.Industri;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndustriRepository extends JpaRepository<Industri, Integer> {
    List<Industri> findByTitleContainingIgnoreCase(String title);
}
