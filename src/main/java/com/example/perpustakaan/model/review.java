package com.example.perpustakaan.model;  // Sesuaikan dengan package yang benar

import jakarta.persistence.*;

@Entity
public class review {  // Nama kelas diubah menjadi Review

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Bisa diganti Integer jika Anda lebih suka konsisten dengan tipe data lainnya

    private String reviewerName;
    private String comment;
    private int rating; // 1-5

    @ManyToOne
    @JoinColumn(name = "book_id")  // Pastikan kolom ini mengarah ke book_id di tabel review
    private Book book;  // Properti untuk relasi ke Book

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getReviewerName() { return reviewerName; }
    public void setReviewerName(String reviewerName) { this.reviewerName = reviewerName; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public Book getBook() { return book; }  // Getter untuk Book
    public void setBook(Book book) { this.book = book; }  // Setter untuk Book
}