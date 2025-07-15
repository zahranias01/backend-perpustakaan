package com.example.perpustakaan.service;

import com.example.perpustakaan.model.Peminjaman;
import com.example.perpustakaan.repository.PeminjamanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PeminjamanService {

    @Autowired
    private PeminjamanRepository peminjamanRepository;

    /**
     * Tambah peminjaman, cek duplikat berdasarkan npm & biblioId
     */
    public String addPeminjaman(Peminjaman peminjaman) {
        boolean exists = peminjamanRepository.existsByNpmAndBiblioId(
                peminjaman.getNpm(), peminjaman.getBiblioId()
        );
        if (exists) {
            return "Buku sudah ada di daftar peminjaman";
        }

        if (peminjaman.getStatus() == null || peminjaman.getStatus().isBlank()) {
            peminjaman.setStatus("PENDING");
        }

        if (peminjaman.getTitle() == null) {
            peminjaman.setTitle("");
        }

        if (peminjaman.getTanggalPinjam() == null) {
            peminjaman.setTanggalPinjam(LocalDate.now());
        }

        peminjamanRepository.save(peminjaman);
        return "Berhasil menambahkan ke peminjaman";
    }

    /**
     * Simpan peminjaman dengan npm eksplisit
     */
    public Peminjaman simpanPeminjaman(String npm, Peminjaman p) {
        p.setNpm(npm);

        if (p.getStatus() == null || p.getStatus().isBlank()) {
            p.setStatus("PENDING");
        }

        if (p.getTitle() == null) {
            p.setTitle("");
        }

        if (p.getTanggalPinjam() == null) {
            p.setTanggalPinjam(LocalDate.now());
        }

        return peminjamanRepository.save(p);
    }

    /**
     * Ambil daftar peminjaman user berdasar npm
     */
    public List<Peminjaman> getPeminjamanByNpm(String npm) {
        return peminjamanRepository.findByNpm(npm);
    }

    /**
     * Update peminjaman berdasar npm & biblioId
     */
    public boolean updatePeminjaman(String npm, int biblioId, Peminjaman updatedPeminjaman) {
        Optional<Peminjaman> optional = peminjamanRepository.findByNpmAndBiblioId(npm, biblioId);

        if (optional.isPresent()) {
            Peminjaman peminjaman = optional.get();

            if (updatedPeminjaman.getTitle() != null) {
                peminjaman.setTitle(updatedPeminjaman.getTitle());
            }

            if (updatedPeminjaman.getStatus() != null) {
                peminjaman.setStatus(updatedPeminjaman.getStatus());
            }

            if (updatedPeminjaman.getTanggalKembali() != null) {
                peminjaman.setTanggalKembali(updatedPeminjaman.getTanggalKembali());
            }

            peminjamanRepository.save(peminjaman);
            return true;
        }

        return false;
    }

    /**
     * Update hanya tanggal kembali
     */
    @Transactional
    public boolean updateTanggalKembali(String npm, int biblioId, LocalDate tanggalKembali) {
        Optional<Peminjaman> optional = peminjamanRepository.findByNpmAndBiblioId(npm, biblioId);

        if (optional.isPresent()) {
            Peminjaman peminjaman = optional.get();
            peminjaman.setTanggalKembali(tanggalKembali);
            peminjamanRepository.save(peminjaman);
            return true;
        }

        return false;
    }

    /**
     * Hapus peminjaman berdasar npm & biblioId
     */
    @Transactional
    public void deletePeminjaman(String npm, int biblioId) {
        System.out.printf(">> Hapus peminjaman: npm=%s, biblioId=%d%n", npm, biblioId);

        boolean exists = peminjamanRepository.existsByNpmAndBiblioId(npm, biblioId);
        System.out.println(">> Exists? " + exists);

        if (!exists) {
            throw new RuntimeException("Peminjaman tidak ditemukan");
        }

        peminjamanRepository.deleteByNpmAndBiblioId(npm, biblioId);
        System.out.println(">> Berhasil dihapus.");
    }
}