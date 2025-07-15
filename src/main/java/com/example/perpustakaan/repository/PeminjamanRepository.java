package com.example.perpustakaan.repository;

import com.example.perpustakaan.model.Peminjaman;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PeminjamanRepository extends JpaRepository<Peminjaman, Long> {

    /**
     * Cari semua peminjaman berdasarkan npm
     */
    List<Peminjaman> findByNpm(String npm);

    /**
     * Cek apakah peminjaman sudah ada (untuk duplikat)
     */
    boolean existsByNpmAndBiblioId(String npm, Integer biblioId);

    /**
     * Cari peminjaman spesifik berdasarkan npm & biblioId
     */
    Optional<Peminjaman> findByNpmAndBiblioId(String npm, Integer biblioId);

    /**
     * Hapus peminjaman berdasarkan npm & biblioId
     */
    void deleteByNpmAndBiblioId(String npm, Integer biblioId);

    // 🔷 Tambahan terkait tanggal:

    /**
    
    List<Peminjaman> findByNpmAndTanggalKembaliIsNull(String npm);

    /**
     * Cari semua peminjaman yang dikembalikan pada tanggal tertentu
     */
    List<Peminjaman> findByTanggalKembali(LocalDate tanggalKembali);

    /**
     * Cari semua peminjaman yang dipinjam pada rentang tanggal tertentu
     */
    List<Peminjaman> findByTanggalPinjamBetween(LocalDate mulai, LocalDate sampai);
}