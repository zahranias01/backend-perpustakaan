package com.example.perpustakaan.controller;

import com.example.perpustakaan.model.Book;
import com.example.perpustakaan.model.ChatbotHistory;
import com.example.perpustakaan.repository.BookRepository;
import com.example.perpustakaan.service.ChatbotHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chatbot")
@CrossOrigin(origins = "http://localhost:5173")
public class ChatbotHistoryController {

    @Autowired
    private ChatbotHistoryService service;

    // 🔸 Mendapatkan semua riwayat chatbot
    @GetMapping("/all")
    public List<ChatbotHistory> getAllChatbotHistories() {
        return service.getAllChatbotHistories();
    }

    // 🔸 Mendapatkan riwayat chatbot berdasarkan ID
    @GetMapping("/{id}")
    public ResponseEntity<ChatbotHistory> getChatbotHistoryById(@PathVariable Long id) {
        Optional<ChatbotHistory> history = service.getChatbotHistoryById(id);
        return history.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔸 Mendapatkan riwayat chatbot berdasarkan NPM
    @GetMapping("/npm/{npm}")
    public List<ChatbotHistory> getChatbotHistoryByNpm(@PathVariable String npm) {
        return service.getChatbotHistoryByNpm(npm);
    }

    // 🔸 Menyimpan riwayat chatbot baru
    @PostMapping("/chat/save")
    public ChatbotHistory saveChatbotHistory(@RequestBody ChatbotHistory chatbotHistory) {
        

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String npm = authentication.getName(); // ← ini adalah NPM dari JWT
    
    // 📝 Set NPM ke entitas sebelum disimpan ke DB
    chatbotHistory.setNpm(npm);
        return service.saveChatbotHistory(chatbotHistory);
    }

    // 🔸 Menghapus riwayat chatbot berdasarkan ID
    @DeleteMapping("/delete/{id}")
    public String deleteChatbotHistory(@PathVariable Long id) {
        service.deleteChatbotHistory(id);
        return "Data Chatbot History dengan ID " + id + " berhasil dihapus!";
    } 

    // 🔸 Menghapus semua riwayat berdasarkan NPM user yang sedang login
    @DeleteMapping("/delete/all")
    public ResponseEntity<String> deleteAllChatbotHistoryForCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String npm = authentication.getName(); // Ambil NPM dari token

        service.deleteAllByNpm(npm); // Panggil service untuk hapus semua berdasarkan NPM
        return ResponseEntity.ok("Semua riwayat berhasil dihapus untuk NPM: " + npm);
    }
}