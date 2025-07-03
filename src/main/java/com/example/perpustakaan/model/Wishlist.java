package com.example.perpustakaan.model;

import jakarta.persistence.*;

@Entity
@Table(name = "wishlist")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String npm;

    @Column(name = "biblio_id", nullable = false)
    private Integer biblioId;

    @Column(nullable = false)
    private String title;

    @Column
    private String catatan;

    @Column
    private String status;

    public Wishlist() {}

    public Wishlist(String npm, Integer biblioId, String title, String catatan, String status) {
        this.npm = npm;
        this.biblioId = biblioId;
        this.title = title;
        this.catatan = catatan;
        this.status = status;
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

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
