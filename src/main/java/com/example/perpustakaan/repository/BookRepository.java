package com.example.perpustakaan.repository;

import com.example.perpustakaan.model.Book;
import com.example.perpustakaan.model.BookRatingDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

    // 🔍 Pencarian berdasarkan judul atau kategori (bebas huruf besar kecil)
    @Query("SELECT b FROM Book b WHERE " +
           "LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(b.kategori) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Book> searchBooks(@Param("keyword") String keyword);

    // 🔍 Spring Data method default (jika tidak pakai @Query)
    List<Book> findByTitleContainingIgnoreCaseOrKategoriContainingIgnoreCase(String title, String kategori);

    // 🔍 Pencarian berdasarkan kategori persis
    @Query("SELECT b FROM Book b WHERE LOWER(b.kategori) = LOWER(:category)")
    List<Book> findBooksByCategory(@Param("category") String category);

    // 🔍 Versi default Spring Data untuk kategori
    List<Book> findByKategoriIgnoreCase(String kategori);

    List<Book> findTop3ByTitleContainingIgnoreCase(String keyword);

    // 🔍 Versi default untuk cari berdasarkan judul saja
    List<Book> findByTitleContainingIgnoreCase(String keyword);

    // ⭐ Mendapatkan buku dengan rating tertinggi (berdasarkan review)
    @Query("SELECT new com.example.perpustakaan.model.BookRatingDTO(b.biblioId, b.title, AVG(r.rating)) " +
           "FROM Book b JOIN b.reviews r " +
           "GROUP BY b.biblioId, b.title " +
           "ORDER BY AVG(r.rating) DESC")
    List<BookRatingDTO> findTopRatedBooks();

    // 🔍 Pencarian berdasarkan kategori dan ID buku
    @Query("SELECT b FROM Book b WHERE LOWER(b.kategori) = LOWER(:kategori) AND b.biblioId = :id")
    Optional<Book> findByKategoriAndId(@Param("kategori") String kategori, @Param("id") Integer id);
}
