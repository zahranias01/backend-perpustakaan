package com.example.perpustakaan.controller;

import com.example.perpustakaan.model.Book;
import com.example.perpustakaan.model.ChatResponse;
import com.example.perpustakaan.repository.BookRepository;
import com.example.perpustakaan.service.Classifier;
import com.example.perpustakaan.service.TogetherAIService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ChatController {

    @Autowired
    private TogetherAIService togetherAIService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private Classifier classifier;

    private final List<String> kategoriList = List.of(
            "informatika", "penerbangan", "akuntansi", "industri",
            "sejarah", "agama", "filsafat", "bisnis", "manajemen",
            "elektronika", "bahasa"
    );

    // 🔑 Keyword jam buka
    private final List<String> keywordsJamBuka = List.of(
        "jam buka","jam tutup","jam operasional","jam layanan","jam kerja","jam pelayanan",
        "jam aktif","jam operasional perpustakaan","jam layanan perpustakaan","jam buka perpustakaan",
        "jam tutup perpustakaan","jam kerja perpustakaan","kapan buka","kapan tutup","kapan perpustakaan buka",
        "kapan perpustakaan tutup","perpustakaan buka jam berapa","perpustakaan tutup jam berapa",
        "jam berapa buka","jam berapa tutup","perpustakaan buka","perpustakaan tutup","waktu buka perpustakaan",
        "waktu layanan perpustakaan","jadwal buka perpustakaan","jadwal layanan perpustakaan",
        "perpustakaan nurtanio buka jam berapa","perpustakaan nurtanio tutup jam berapa","jam buka perpustakaan nurtanio",
        "jam tutup perpustakaan nurtanio","buka jam berapa","tutup jam berapa","pukul berapa perpustakaan buka",
        "pukul berapa perpustakaan tutup","operasional perpustakaan","jadwal operasional",
        "jam kerja kampus","jam buka kampus","jamber"
    );

    @PostMapping("/chat")
    public ResponseEntity<?> chat(@RequestBody Map<String, String> payload) {
        String message = payload.get("message");
        System.out.println("📨 Pertanyaan user: " + message);

        try {
            String klasifikasi = classifier.klasifikasikan(message);
            System.out.println("🔍 Klasifikasi pertanyaan: " + klasifikasi);

            if (keywordsJamBuka.stream().anyMatch(message.toLowerCase()::contains)) {
                return ResponseEntity.ok(new ChatResponse(
                        "Perpustakaan Universitas Nurtanio buka pukul 08.00 - 15.00 WIB, Senin sampai Jumat."));
            }

            if (klasifikasi.equalsIgnoreCase("lokal")) {
                for (String keyword : kategoriList) {
                    if (message.toLowerCase().contains(keyword)) {

                        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(keyword);

                        //Parse jumlah buku jika user menyebut angka
                        Pattern p = Pattern.compile("(\\d+)\\s*buku");
                        Matcher m = p.matcher(message);
                        List<Book> hasil;

                        if (m.find()) {
                            int jumlah = Integer.parseInt(m.group(1));
                            hasil = books.stream().limit(jumlah).collect(Collectors.toList());
                        } else {
                            hasil = books;
                        }

                        System.out.println("🔎 Cari berdasarkan judul, keyword: " + keyword);
                        System.out.println("📚 Jumlah buku ditemukan: " + hasil.size());

                        if (hasil.isEmpty()) {
                            return ResponseEntity.ok(new ChatResponse("Maaf, tidak ditemukan buku kategori " + keyword + "."));
                        }

                        String daftar = hasil.stream()
                                .map(book -> "- " + book.getTitle())
                                .collect(Collectors.joining("\n"));

                        return ResponseEntity.ok(new ChatResponse(
                                "Berikut adalah " + hasil.size() + " buku kategori " + keyword + ":\n" + daftar));
                    }
                }
                return ResponseEntity.ok(new ChatResponse("Kategori buku tidak dikenali."));
            }

            return togetherAIService.getChatCompletion(message)
                    .map(ResponseEntity::ok)
                    .onErrorResume(e -> {
                        String errMsg = e.getMessage();
                        if (errMsg != null && errMsg.contains("429")) {
                            return Mono.just(ResponseEntity
                                    .status(HttpStatus.TOO_MANY_REQUESTS)
                                    .body(new ChatResponse("⚠️ AI sedang sibuk, silakan coba beberapa saat lagi.")));
                        } else if (errMsg != null && errMsg.contains("422")) {
                            return Mono.just(ResponseEntity
                                    .status(HttpStatus.UNPROCESSABLE_ENTITY)
                                    .body(new ChatResponse("⚠️ Gagal memproses pertanyaan ke AI. Coba pertanyaan lain.")));
                        }
                        return Mono.just(ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ChatResponse("⚠️ Terjadi kesalahan di server.")));
                    }).block();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ChatResponse("⚠️ Kesalahan internal server."));
        }
    }
}
