package com.example.perpustakaan.controller;

import com.example.perpustakaan.model.Book;
import com.example.perpustakaan.model.ChatResponse;
import com.example.perpustakaan.model.ChatbotHistory;
import com.example.perpustakaan.repository.BookRepository;
import com.example.perpustakaan.repository.ChatbotHistoryRepository;
import com.example.perpustakaan.service.Classifier;
import com.example.perpustakaan.service.TogetherAIService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.regex.*;
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
    private ChatbotHistoryRepository chatbotHistoryRepository;

    @Autowired
    private Classifier classifier;

    private final List<String> kategoriList = List.of(
            "informatika", "penerbangan", "akuntansi", "industri",
            "sejarah", "agama", "filsafat", "bisnis", "manajemen",
            "elektronika", "bahasa","sistem","computer","komputer","pemrograman","informasi","aircraft","flight","teknik","pajak","ekonomi","kamus"
    );

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
        String npm = payload.get("npm");
        String message = payload.get("message");
        System.out.println("📨 Pertanyaan user: " + message);

        try {
            // Ambil histori terakhir (untuk context prompt)
            List<ChatbotHistory> histories = chatbotHistoryRepository.findTop5ByNpmOrderByCreatedAtDesc(npm);
            Collections.reverse(histories); // urutkan dari lama ke baru

            StringBuilder prompt = new StringBuilder("Percakapan sebelumnya:\n");
            for (ChatbotHistory h : histories) {
                prompt.append("User: ").append(h.getPertanyaan()).append("\n");
                prompt.append("AI: ").append(h.getAiResponse()).append("\n");
            }
            prompt.append("User: ").append(message).append("\n");
            prompt.append("AI:");

            String klasifikasi = classifier.klasifikasikan(message);
            System.out.println("🔍 Klasifikasi pertanyaan: " + klasifikasi);

            // 🔑 Cek pertanyaan jam buka
            if (keywordsJamBuka.stream().anyMatch(message.toLowerCase()::contains)) {
                String jamBuka = "Perpustakaan Universitas Nurtanio buka pukul 08.00 - 15.00 WIB, Senin sampai Jumat.";

                // Simpan ke DB
                ChatbotHistory history = new ChatbotHistory();
                history.setNpm(npm);
                history.setPertanyaan(message);
                history.setAiResponse(jamBuka);
                chatbotHistoryRepository.save(history);

                return ResponseEntity.ok(new ChatResponse(jamBuka));
            }

            // 🔑 Cek kategori lokal
            if (klasifikasi.equalsIgnoreCase("lokal")) {
                for (String keyword : kategoriList) {
                    if (message.toLowerCase().contains(keyword)) {
                        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(keyword);

                        Pattern p = Pattern.compile("(\\d+)\\s*buku");
                        Matcher m = p.matcher(message);
                        List<Book> hasil;

                        if (m.find()) {
                            int jumlah = Integer.parseInt(m.group(1));
                            hasil = books.stream().limit(jumlah).collect(Collectors.toList());
                        } else {
                            hasil = books;
                        }

                        String responseText;
                        if (hasil.isEmpty()) {
                            responseText = "Maaf, tidak ditemukan buku kategori " + keyword + ".";
                        } else {
                            String daftar = hasil.stream()
                                    .map(book -> "- " + book.getTitle())
                                    .collect(Collectors.joining("\n"));

                            responseText = "Berikut adalah " + hasil.size() + " buku kategori " + keyword + ":\n" + daftar;
                        }

                        // Simpan ke DB
                        ChatbotHistory history = new ChatbotHistory();
                        history.setNpm(npm);
                        history.setPertanyaan(message);
                        history.setAiResponse(responseText);
                        chatbotHistoryRepository.save(history);

                        return ResponseEntity.ok(new ChatResponse(responseText));
                    }
                }
            }

            // 🔑 Jika bukan keyword jam buka atau kategori -> kirim ke TogetherAI dengan prompt context
            String aiResponse = togetherAIService.getChatCompletion(prompt.toString()).block().getReply();

            // Simpan ke DB
            ChatbotHistory history = new ChatbotHistory();
            history.setNpm(npm);
            history.setPertanyaan(message);
            history.setAiResponse(aiResponse);
            chatbotHistoryRepository.save(history);

            return ResponseEntity.ok(new ChatResponse(aiResponse));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ChatResponse("⚠️ Terjadi kesalahan di server."));
        }
    }
}
