package com.example.perpustakaan.model;

import jakarta.persistence.*;

@Entity
@Table(name = "listbuku")
public class Book {

    @Id
    @Column(name = "biblio_id") // Sesuaikan dengan nama kolom di database
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer biblioId;
    
    @Column(name = "title") // Explicit mapping ke kolom database
    private String title;
    
    @Column(name = "isbn_issn") // Perbaikan: nama kolom harus sama dengan database
    private String isbnIssn; // Ubah nama field agar konsisten (camelCase)

    @Column(name = "kategori") // Tambahan kolom kategori
    private String kategori;

    // Getter dan Setter
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

    public String getIsbnIssn() { // Perbaikan getter
        return isbnIssn;
    }

    public void setIsbnIssn(String isbnIssn) {
        this.isbnIssn = isbnIssn;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
}
