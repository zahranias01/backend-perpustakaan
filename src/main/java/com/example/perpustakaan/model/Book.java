package com.example.perpustakaan.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "listbuku")
public class Book {

    @Id
    @Column(name = "biblio_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer biblioId;

    @Column(name = "title")
    private String title;

    @Column(name = "edition")
    private String edition;

    @Column(name = "isbn_issn")
    private String isbnIssn;

    @Column(name = "publish_year")
    private String publish_year;

    @Column(name = "collation")
    private String collation;

    @Column(name = "language_id")
    private String language_id;

    @Column(name = "input_date")
    private String input_date;

    @Column(name = "last_update")
    private String last_update;

    @Column(name = "kategori")
    private String kategori;

    @Column(name = "rating")
    private Double rating;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;


    // === Getter dan Setter ===

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

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    @JsonProperty("isbn_issn")
    public String getIsbnIssn() {
        return isbnIssn;
    }

    public void setIsbnIssn(String isbnIssn) {
        this.isbnIssn = isbnIssn;
    }

    @JsonProperty("publish_year")
    public String getPublishYear() {
        return publish_year;
    }

    public void setPublishYear(String publish_year) {
        this.publish_year = publish_year;
    }

    public String getCollation() {
        return collation;
    }

    public void setCollation(String collation) {
        this.collation = collation;
    }

    @JsonProperty("language_id")
    public String getLanguageId() {
        return language_id;
    }

    public void setLanguageId(String language_id) {
        this.language_id = language_id;
    }

    @JsonProperty("input_date")
    public String getInputDate() {
        return input_date;
    }

    public void setInputDate(String input_date) {
        this.input_date = input_date;
    }

    @JsonProperty("last_update")
    public String getLastUpdate() {
        return last_update;
    }

    public void setLastUpdate(String last_update) {
        this.last_update = last_update;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
    
}