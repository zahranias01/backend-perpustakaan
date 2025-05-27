package com.example.perpustakaan.repository;

import com.example.perpustakaan.model.Filsafat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilsafatRepository extends JpaRepository<Filsafat, Integer> {
    List<Filsafat> findByTitleContainingIgnoreCase(String title);
}
