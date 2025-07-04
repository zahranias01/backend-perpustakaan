package com.example.perpustakaan.controller;

import com.example.perpustakaan.model.Book;
import com.example.perpustakaan.model.Temp;
import com.example.perpustakaan.repository.BookRepository;
import com.example.perpustakaan.repository.ReviewRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    public ReviewController(ReviewRepository reviewRepository, BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
    }

    // Tambah review berdasarkan ID buku
    @PostMapping("/{biblioId}")
    public ResponseEntity<String> addReview(@PathVariable Integer biblioId, @RequestBody Temp review) {
        return bookRepository.findById(biblioId).map(book -> {
            review.setBook(book);
            reviewRepository.save(review);
            return ResponseEntity.ok("Review berhasil ditambahkan.");
        }).orElse(ResponseEntity.notFound().build());
    }

    // Ambil review berdasarkan ID buku
    @GetMapping("/{biblioId}")
    public List<Temp> getReviews(@PathVariable Integer biblioId) {
        return reviewRepository.findByBook_BiblioId(biblioId);
    }
}