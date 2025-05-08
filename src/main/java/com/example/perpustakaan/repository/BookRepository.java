package com.example.perpustakaan.repository;

import com.example.perpustakaan.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT b FROM Book b WHERE " +
        "LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
        "LOWER(b.isbnIssn) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Book> searchBooks(@Param("keyword") String keyword);

    // Untuk pencarian berdasarkan kategori
    @Query("SELECT b FROM Book b WHERE LOWER(b.kategori) = LOWER(:category)")
    List<Book> findBooksByCategory(@Param("category") String category);
}
