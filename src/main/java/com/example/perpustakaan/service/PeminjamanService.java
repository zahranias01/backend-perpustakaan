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

    // Simpan peminjaman baru (USER)
    public Peminjaman simpanPeminjaman(String npm, Peminjaman p) {
        p.setNpm(npm);
        if (p.getStatus() == null || p.getStatus().isBlank()) {
            p.setStatus("PENDING");
        }
        if (p.getTanggalPengajuan() == null) {
            p.setTanggalPengajuan(LocalDate.now());
        }
        return peminjamanRepository.save(p);
    }

    // Cari semua peminjaman berdasarkan npm
    public List<Peminjaman> getPeminjamanByNpm(String npm) {
        return peminjamanRepository.findByNpm(npm);
    }

    // Update tanggal dikembalikan (USER)
    @Transactional
    public boolean updateTanggalDikembalikan(String npm, int biblioId, LocalDate tgl) {
        Optional<Peminjaman> optional = peminjamanRepository.findByNpmAndBiblioId(npm, biblioId);
        if (optional.isPresent()) {
            Peminjaman p = optional.get();
            p.setTanggalDikembalikan(tgl);
            p.setStatus("DIKEMBALIKAN");

            // HITUNG DENDA
            if (p.getTanggalKembali() != null && tgl.isAfter(p.getTanggalKembali())) {
                int hariTerlambat = (int) java.time.temporal.ChronoUnit.DAYS.between(p.getTanggalKembali(), tgl);
                int dendaPerHari = 1000; // misal Rp 1000 per hari
                p.setDenda(hariTerlambat * dendaPerHari);
            } else {
                p.setDenda(0);
            }
            return true;
        }
        return false;
    }

    // Hapus peminjaman (USER)
    @Transactional
    public void deletePeminjaman(String npm, int biblioId) {
        peminjamanRepository.deleteByNpmAndBiblioId(npm, biblioId);
    }

    // Admin - get all
    public List<Peminjaman> getAllPeminjaman() {
        return peminjamanRepository.findAll();
    }

    // Admin - approve
    @Transactional
    public boolean approvePeminjaman(Long id, LocalDate tglPinjam, LocalDate tglKembali) {
        Optional<Peminjaman> optional = peminjamanRepository.findById(id);
        if(optional.isEmpty()) return false;
            Peminjaman p = optional.get();

            // Cek apakah buku sedang dipinjam user lain
            boolean alreadyLoaned = peminjamanRepository.existsByBiblioIdAndStatus(p.getBiblioId(), "APPROVED");
            if(!alreadyLoaned) {
                // jika sudah ada, reject otomatis
                p.setStatus("REJECTED");
                peminjamanRepository.save(p);
                return false; // bisa pakai return false untuk kasih tahu frontend
            }

            // jika tidak ada, approve
            p.setStatus("APPROVED");
            p.setTanggalPinjam(tglPinjam);
            p.setTanggalKembali(tglKembali);
            peminjamanRepository.save(p);
            return true;
    }


    // Admin - update status
    @Transactional
    public boolean updateStatusPeminjaman(Long id, String status) {
        Optional<Peminjaman> optional = peminjamanRepository.findById(id);
        if (optional.isPresent()) {
            Peminjaman p = optional.get();
            p.setStatus(status);
            return true;
        }
        return false;
    }

    // Admin - update tanggal kembali
    @Transactional
    public boolean updateTanggalKembaliAdmin(Long id, LocalDate tanggalKembali) {
        Optional<Peminjaman> optional = peminjamanRepository.findById(id);
        if (optional.isPresent()) {
            Peminjaman p = optional.get();
            p.setTanggalKembali(tanggalKembali);
            return true;
        }
        return false;
    }

    // Admin - delete by id
    @Transactional
    public void deletePeminjamanById(Long id) {
        peminjamanRepository.deleteById(id);
    }

    @Transactional
    public boolean cancelPeminjamanUser(String npm, int biblioId) {
        Optional<Peminjaman> optional = peminjamanRepository.findByNpmAndBiblioId(npm, biblioId);
        if(optional.isPresent()) {
            Peminjaman p = optional.get();
            if(!p.getStatus().equalsIgnoreCase("PENDING")) {
                // Hanya hapus data user-side, jangan ubah status global
                p.setNpm(null); // atau tandai "dihapus user"
                return true;
            }
            return false;
        }
        return false;
    }

}
