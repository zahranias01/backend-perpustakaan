package com.example.perpustakaan.repository;

import com.example.perpustakaan.model.Akutansi;
import com.example.perpustakaan.model.Sejarah;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SejarahRepository extends JpaRepository<Sejarah, Integer> {
    List<Sejarah> findByTitleContainingIgnoreCase(String title);
}