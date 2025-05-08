package com.example.perpustakaan.service;

import com.example.perpustakaan.model.Book;
import com.example.perpustakaan.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bukuRepository;

    // Method untuk mencari buku berdasarkan keyword
    public List<Book> searchBuku(String keyword) {
        // Menggunakan query custom yang ada di repository untuk mencari buku berdasarkan title atau isbnIssn
        return bukuRepository.searchBooks(keyword);  // Pastikan tidak ada karakter tersembunyi setelah baris ini
    }  
}
