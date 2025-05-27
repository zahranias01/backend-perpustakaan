package com.example.perpustakaan.controller;

import com.example.perpustakaan.model.ChatMessage;
import com.example.perpustakaan.model.ChatResponse;
import com.example.perpustakaan.service.TogetherAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final TogetherAIService togetherAIService;

    @Autowired
    public ChatController(TogetherAIService togetherAIService) {
        this.togetherAIService = togetherAIService;
    }

    @PostMapping
    public Mono<ResponseEntity<ChatResponse>> handleChat(@RequestBody ChatMessage userMessage) {
        return togetherAIService.getChatCompletion(userMessage.getMessage())
            .map(chatResponse -> ResponseEntity.ok(chatResponse))
            .onErrorResume(e -> {

                ChatResponse errorResponse = new ChatResponse("Maaf, terjadi kesalahan saat memproses permintaan Anda.");
                return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse));
            });
    }
}
