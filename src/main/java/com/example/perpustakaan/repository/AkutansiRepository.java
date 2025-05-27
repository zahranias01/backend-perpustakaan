package com.example.perpustakaan.repository;

import com.example.perpustakaan.model.Akutansi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AkutansiRepository extends JpaRepository<Akutansi, Integer> {
    List<Akutansi> findByTitleContainingIgnoreCase(String title);
}
