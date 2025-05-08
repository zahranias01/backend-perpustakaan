package com.example.perpustakaan.controller;

import com.example.perpustakaan.model.review;  // Gunakan Review dengan huruf kapital
import com.example.perpustakaan.repository.BookRepository;
import com.example.perpustakaan.repository.ReviewRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    public ReviewController(ReviewRepository reviewRepository, BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
    }

    @PostMapping("/{bookId}")
    public ResponseEntity<String> addReview(@PathVariable Integer bookId, @RequestBody review review) {
        return bookRepository.findById(bookId).map(book -> {
            review.setBook(book);
            reviewRepository.save(review);
            return ResponseEntity.ok("Review added successfully.");
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{bookId}")
    public List<review> getReviews(@PathVariable Integer bookId) {
        return reviewRepository.findByBook_BiblioId(bookId);
    }
}
