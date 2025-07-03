package com.example.perpustakaan.repository;
import com.example.perpustakaan.model.AdministrasiNegara;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdministrasiNegaraRepository extends JpaRepository<AdministrasiNegara, Integer> {
    List<AdministrasiNegara> findByTitleContainingIgnoreCase(String title);
}
