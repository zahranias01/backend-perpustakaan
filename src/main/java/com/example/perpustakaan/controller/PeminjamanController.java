package com.example.perpustakaan.controller;

import com.example.perpustakaan.model.Peminjaman;
import com.example.perpustakaan.service.PeminjamanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

    // ============ USER ============

    @PostMapping("/{npm}")
    public ResponseEntity<?> pinjam(@PathVariable String npm, @RequestBody Peminjaman p) {
        try {
            p.setStatus("pending"); 
            p.setTanggalPengajuan(LocalDate.now());
            p.setTanggalPinjam(null); 
            Peminjaman baru = peminjamanService.simpanPeminjaman(npm, p);
            return ResponseEntity.status(HttpStatus.CREATED).body(baru);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gagal menyimpan peminjaman: " + e.getMessage());
        }
    }

    @GetMapping("/{npm}")
    public ResponseEntity<?> getPeminjamanByNpm(@PathVariable String npm) {
        List<Peminjaman> list = peminjamanService.getPeminjamanByNpm(npm);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{npm}/{biblioId}/kembali")
    public ResponseEntity<?> kembalikanPeminjaman(
            @PathVariable String npm,
            @PathVariable int biblioId,
            @RequestBody Map<String, String> body
    ) {
        try {
            String tanggalStr = body.get("tanggalDikembalikan"); // dari frontend
            LocalDate tgl = LocalDate.parse(tanggalStr);

            boolean updated = peminjamanService.updateTanggalDikembalikan(npm, biblioId, tgl);
            if (updated) {
                return ResponseEntity.ok("Buku berhasil dikembalikan");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data peminjaman tidak ditemukan");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gagal mengembalikan buku: " + e.getMessage());
        }
    }

    @DeleteMapping("/{npm}/{biblioId}")
    public ResponseEntity<?> deletePeminjaman(
            @PathVariable String npm,
            @PathVariable int biblioId
    ) {
        try {
            peminjamanService.deletePeminjaman(npm, biblioId);
            return ResponseEntity.ok("Data peminjaman berhasil dihapus");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gagal menghapus data peminjaman: " + e.getMessage());
        }
    }

    // ============ ADMIN ============

    @GetMapping("/admin/all")
    public ResponseEntity<?> getAllPeminjaman() {
        List<Peminjaman> list = peminjamanService.getAllPeminjaman();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/admin/{id}/approve")
    public ResponseEntity<?> approvePeminjaman(@PathVariable Long id) {
        try {
            boolean result = peminjamanService.approvePeminjaman(id, LocalDate.now(), LocalDate.now().plusDays(7));
            if(result) {
                return ResponseEntity.ok(true); // berhasil approve
            } else {
                return ResponseEntity.ok(false); // otomatis reject karena buku sedang dipinjam
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gagal approve peminjaman: " + e.getMessage());
        }
    }


    @PutMapping("/admin/{id}/status")
    public ResponseEntity<?> updateStatusPeminjaman(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        try {
            boolean updated = peminjamanService.updateStatusPeminjaman(id, status);
            if (updated) {
                return ResponseEntity.ok("Status berhasil diupdate");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Peminjaman tidak ditemukan");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gagal update status: " + e.getMessage());
        }
    }

    @PutMapping("/admin/{id}/tanggal_kembali")
    public ResponseEntity<?> updateTanggalKembaliAdmin(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {
        try {
            String tanggalStr = body.get("tanggalKembali");
            boolean updated = peminjamanService.updateTanggalKembaliAdmin(id, LocalDate.parse(tanggalStr));
            if (updated) {
                return ResponseEntity.ok("Tanggal kembali berhasil diupdate");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Peminjaman tidak ditemukan");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gagal update tanggal kembali: " + e.getMessage());
        }
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deletePeminjamanAdmin(@PathVariable Long id) {
        try {
            peminjamanService.deletePeminjamanById(id);
            return ResponseEntity.ok("Data peminjaman berhasil dihapus");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gagal menghapus data peminjaman: " + e.getMessage());
        }
    }

    @PutMapping("/{npm}/{biblioId}/cancel-user")
    public ResponseEntity<?> cancelPeminjamanUser(@PathVariable String npm, @PathVariable int biblioId) {
        try {
            boolean cancelled = peminjamanService.cancelPeminjamanUser(npm, biblioId);
            if(cancelled) return ResponseEntity.ok("Peminjaman dibatalkan user-side");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Peminjaman tidak ditemukan");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal membatalkan: " + e.getMessage());
        }
    }

}
