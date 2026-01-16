package com.example.perpustakaan.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "peminjaman")
public class Peminjaman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String npm;

    @Column(name = "biblio_id", nullable = false)
    private Integer biblioId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String status = "pending"; // ✅ default sesuai query SQL

    // === tanggal ===
    @Column(name = "tanggal_pengajuan", nullable = false, columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDate tanggalPengajuan = LocalDate.now(); // ✅ otomatis isi hari ini

    @Column(name = "tanggal_pinjam")
    private LocalDate tanggalPinjam; // ✅ diisi admin saat approve

    @Column(name = "tanggal_kembali")
    private LocalDate tanggalKembali; // ✅ batas waktu pengembalian

    @Column(name = "tanggal_dikembalikan")
    private LocalDate tanggalDikembalikan; // ✅ diisi user via kalender saat balikin

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer denda = 0; // ✅ default 0

    public Peminjaman() {
        this.status = "pending";
        this.tanggalPengajuan = LocalDate.now();
    }

    public Peminjaman(String npm, Integer biblioId, String title) {
        this.npm = npm;
        this.biblioId = biblioId;
        this.title = title;
        this.status = "pending";
        this.tanggalPengajuan = LocalDate.now();
    }

    // === Getter Setter ===
    public Long getId() {
        return id;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public Integer getBiblioId() {
        return biblioId;
    }

    public void setBiblioId(Integer biblioId) {
        this.biblioId = biblioId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = (status == null || status.isBlank()) ? "pending" : status;
    }

    public LocalDate getTanggalPengajuan() {
        return tanggalPengajuan;
    }

    public void setTanggalPengajuan(LocalDate tanggalPengajuan) {
        this.tanggalPengajuan = tanggalPengajuan;
    }

    public LocalDate getTanggalPinjam() {
        return tanggalPinjam;
    }

    public void setTanggalPinjam(LocalDate tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }

    public LocalDate getTanggalKembali() {
        return tanggalKembali;
    }

    public void setTanggalKembali(LocalDate tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }

    public LocalDate getTanggalDikembalikan() {
        return tanggalDikembalikan;
    }

    public void setTanggalDikembalikan(LocalDate tanggalDikembalikan) {
        this.tanggalDikembalikan = tanggalDikembalikan;
    }

    public Integer getDenda() {
        return denda;
    }

    public void setDenda(Integer denda) {
        this.denda = denda;
    }
}
