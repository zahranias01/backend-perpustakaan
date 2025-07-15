package com.example.perpustakaan.controller;

import com.example.perpustakaan.model.Peminjaman;
import com.example.perpustakaan.service.PeminjamanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/peminjaman")
@CrossOrigin(
    origins = {
        "http://localhost:5173",
        "https://nurtanio-perpustakaan.netlify.app"
    },
    allowCredentials = "true"
)
public class PeminjamanController {

    @Autowired
    private PeminjamanService peminjamanService;

    /**
     * Helper: Validasi authentication & npm
     */
    private ResponseEntity<String> checkAuthenticationAndAuthorization(String npm) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            System.out.println("[AuthCheck] User not authenticated");
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Silakan login terlebih dahulu");
        }

        String loggedInNpm = auth.getName();
        System.out.printf("[AuthCheck] Path npm: %s | JWT npm: %s%n", npm, loggedInNpm);

        if (!npm.trim().equalsIgnoreCase(loggedInNpm.trim())) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Akses ditolak");
        }

        return null; // valid
    }

    /**
     * POST: Ajukan peminjaman
     */
    @PostMapping("/{npm}")
    public ResponseEntity<?> pinjam(
            @PathVariable String npm,
            @RequestBody Peminjaman p
    ) {
        ResponseEntity<String> authCheck = checkAuthenticationAndAuthorization(npm);
        if (authCheck != null) return authCheck;

        try {
            // pastikan tanggalPinjam terisi jika null
            if (p.getTanggalPinjam() == null) {
                p.setTanggalPinjam(LocalDate.now());
            }

            Peminjaman baru = peminjamanService.simpanPeminjaman(npm, p);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(baru);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gagal menyimpan peminjaman: " + e.getMessage());
        }
    }

    /**
     * GET: Lihat daftar peminjaman user
     */
    @GetMapping("/{npm}")
    public ResponseEntity<?> getPeminjamanByNpm(@PathVariable String npm) {
        ResponseEntity<String> authCheck = checkAuthenticationAndAuthorization(npm);
        if (authCheck != null) return authCheck;

        List<Peminjaman> list = peminjamanService.getPeminjamanByNpm(npm);
        return ResponseEntity.ok(list);
    }

    /**
     * PUT: Update peminjaman
     */
    @PutMapping("/{npm}/{biblioId}")
    public ResponseEntity<?> updatePeminjaman(
            @PathVariable String npm,
            @PathVariable int biblioId,
            @RequestBody Peminjaman updatedPeminjaman
    ) {
        ResponseEntity<String> authCheck = checkAuthenticationAndAuthorization(npm);
        if (authCheck != null) return authCheck;

        boolean updated = peminjamanService.updatePeminjaman(npm, biblioId, updatedPeminjaman);

        if (updated) {
            return ResponseEntity.ok("Data peminjaman berhasil diperbarui");
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Data peminjaman tidak ditemukan");
        }
    }

    /**
     * PUT: Set tanggal kembali
     */
    @PutMapping("/{npm}/{biblioId}/kembali")
    public ResponseEntity<?> kembalikanPeminjaman(
            @PathVariable String npm,
            @PathVariable int biblioId
    ) {
        ResponseEntity<String> authCheck = checkAuthenticationAndAuthorization(npm);
        if (authCheck != null) return authCheck;

        try {
            boolean updated = peminjamanService.updateTanggalKembali(npm, biblioId, LocalDate.now());
            if (updated) {
                return ResponseEntity.ok("Tanggal kembali berhasil dicatat");
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Data peminjaman tidak ditemukan");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gagal mencatat tanggal kembali: " + e.getMessage());
        }
    }

    /**
     * DELETE: Hapus peminjaman
     */
    @DeleteMapping("/{npm}/{biblioId}")
    public ResponseEntity<?> deletePeminjaman(
            @PathVariable String npm,
            @PathVariable int biblioId
    ) {
        ResponseEntity<String> authCheck = checkAuthenticationAndAuthorization(npm);
        if (authCheck != null) return authCheck;

        try {
            peminjamanService.deletePeminjaman(npm, biblioId);
            return ResponseEntity.ok("Data peminjaman berhasil dihapus");
        } catch (Exception e) {
            e.printStackTrace();

            if ("Peminjaman tidak ditemukan".equalsIgnoreCase(e.getMessage())) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(e.getMessage());
            }

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gagal menghapus data peminjaman: " + e.getMessage());
        }
    }
}