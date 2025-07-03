package com.example.perpustakaan.controller;

import com.example.perpustakaan.model.Book;
import com.example.perpustakaan.model.BookRatingDTO;
import com.example.perpustakaan.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/buku")
@CrossOrigin(origins = "http://localhost:5173")
public class BookController {

    @Autowired
    private BookRepository bukuRepository;

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBuku(@RequestParam String keyword) {
        System.out.println("Searching for keyword: " + keyword);  // Debugging
        List<Book> results = bukuRepository.searchBooks(keyword);
        if (results.isEmpty()) {
            System.out.println("No books found for keyword: " + keyword);  // Debugging
        } else {
            System.out.println("Found " + results.size() + " books for keyword: " + keyword);  // Debugging
        }
        return results.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(results);
    }

    @GetMapping("/searchByCategory")
    public ResponseEntity<List<Book>> searchByCategory(@RequestParam String category) {
        List<Book> results = bukuRepository.findBooksByCategory(category);
        return results.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(results);
    }

    @GetMapping("/top-rated")
    public ResponseEntity<List<BookRatingDTO>> getTopRatedBooks() {
        List<BookRatingDTO> topBooks = bukuRepository.findTopRatedBooks();
        return ResponseEntity.ok(topBooks);
    }

    // ✅ Detail buku global: /api/buku/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {
        return bukuRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Detail buku berdasarkan kategori: /api/buku/kategori/{kategori}/detail/{id}
    @GetMapping("/kategori/{kategori}/detailbuku/{id}")
    public ResponseEntity<Book> getBookByKategoriAndId(
            @PathVariable String kategori,
            @PathVariable Integer id
    ) {
        Optional<Book> book = bukuRepository.findByKategoriAndId(kategori, id);
        return book.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    
}

