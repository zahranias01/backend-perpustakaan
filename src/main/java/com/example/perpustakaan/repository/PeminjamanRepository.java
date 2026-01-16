package com.example.perpustakaan.repository;

import com.example.perpustakaan.model.Peminjaman;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PeminjamanRepository extends JpaRepository<Peminjaman, Long> {

    List<Peminjaman> findByNpm(String npm);

    boolean existsByNpmAndBiblioId(String npm, Integer biblioId);

    Optional<Peminjaman> findByNpmAndBiblioId(String npm, Integer biblioId);

    void deleteByNpmAndBiblioId(String npm, Integer biblioId);

    List<Peminjaman> findByNpmAndTanggalKembaliIsNull(String npm);

    List<Peminjaman> findByTanggalKembali(LocalDate tanggalKembali);

    List<Peminjaman> findByTanggalPinjamBetween(LocalDate mulai, LocalDate sampai);

     // Cek apakah ada buku dengan biblioId tertentu yang statusnya APPROVED
    boolean existsByBiblioIdAndStatus(Integer biblioId, String status);

    List<Peminjaman> findByBiblioIdAndStatusAndTanggalDikembalikanIsNull(Integer biblioId, String status);
}
