package com.example.perpustakaan.repository;

import com.example.perpustakaan.model.Informatika;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InformatikaRepository extends JpaRepository<Informatika, Integer> {
    List<Informatika> findByTitleContainingIgnoreCase(String title);
}
