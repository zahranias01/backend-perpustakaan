package com.example.perpustakaan.repository;

import com.example.perpustakaan.model.Penerbangan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PenerbanganRepository extends JpaRepository<Penerbangan, Integer> {
    List<Penerbangan> findByTitleContainingIgnoreCase(String title);
}
