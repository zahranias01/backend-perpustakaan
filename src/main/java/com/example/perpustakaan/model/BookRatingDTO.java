package com.example.perpustakaan.model;

public class BookRatingDTO {
    private Integer id;
    private String title;
    private Double averageRating;

    public BookRatingDTO(Integer id, String title, Double averageRating) {
        this.id = id;
        this.title = title;
        this.averageRating = averageRating;
    }

    // Getter & Setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }
}