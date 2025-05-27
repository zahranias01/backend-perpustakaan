package com.example.perpustakaan.repository;

import com.example.perpustakaan.model.Elektronika;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElektronikaRepository extends JpaRepository<Elektronika, Integer> {
    List<Elektronika> findByTitleContainingIgnoreCase(String title);
}
