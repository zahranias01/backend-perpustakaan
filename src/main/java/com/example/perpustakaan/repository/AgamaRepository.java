package com.example.perpustakaan.repository;

import com.example.perpustakaan.model.Agama;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgamaRepository extends JpaRepository<Agama, Integer> {
    List<Agama> findByTitleContainingIgnoreCase(String title);
}