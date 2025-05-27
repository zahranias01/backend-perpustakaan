package com.example.perpustakaan.repository;

import com.example.perpustakaan.model.Bisnis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BisnisRepository extends JpaRepository<Bisnis, Integer> {
    List<Bisnis> findByTitleContainingIgnoreCase(String title);
}

