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
    private String status = "PENDING"; // ✅ default value di Java

    @Column(name = "tanggal_pinjam", nullable = false, columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDate tanggalPinjam; // ✅ tanggal pinjam otomatis current_date

    @Column(name = "tanggal_kembali")
    private LocalDate tanggalKembali; // opsional

    public Peminjaman() {
        // default constructor tetap set status & tanggalPinjam
        this.status = "PENDING";
        this.tanggalPinjam = LocalDate.now();
    }

    public Peminjaman(String npm, Integer biblioId, String title) {
        this.npm = npm;
        this.biblioId = biblioId;
        this.title = title;
        this.status = "PENDING";
        this.tanggalPinjam = LocalDate.now();
    }

    public Peminjaman(String npm, Integer biblioId, String title, String status) {
        this.npm = npm;
        this.biblioId = biblioId;
        this.title = title;
        this.status = status != null ? status : "PENDING";
        this.tanggalPinjam = LocalDate.now();
    }

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
        if (status == null || status.isBlank()) {
            this.status = "PENDING";
        } else {
            this.status = status;
        }
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
}