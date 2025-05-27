package com.example.perpustakaan.repository;

import com.example.perpustakaan.model.Bahasa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BahasaRepository extends JpaRepository<Bahasa, Integer> {
    List<Bahasa> findByTitleContainingIgnoreCase(String title);
}
