package com.example.perpustakaan.service;

import com.example.perpustakaan.model.ChatResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TogetherAIService {

    private WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${together.api.key}")
    private String apiKey;

    @Value("${together.api.url}")
    private String apiUrl;

    @Value("${together.api.model}")
    private String model;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
            .baseUrl(apiUrl)
            .defaultHeader(HttpHeaders.AUTHORIZATION, apiKey)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    public Mono<ChatResponse> getChatCompletion(String userMessage) {
        ObjectNode request = objectMapper.createObjectNode();
        request.put("model", model);
        request.put("temperature", 0.7);
        request.put("max_tokens", 512);

        ArrayNode messages = objectMapper.createArrayNode();

        ObjectNode systemMessage = objectMapper.createObjectNode();
        systemMessage.put("role", "system");
        systemMessage.put("content",
            "Anda adalah asisten perpustakaan universitas. Jawaban harus menggunakan Bahasa Indonesia. "
            + "Jangan menjawab pertanyaan di luar topik buku, perpustakaan, rekomendasi, atau layanan kampus."
            + "Kamu adalah asisten perpustakaan. Tugasmu hanya menjawab sesuai permintaan pengguna. "
            + "Jika diminta sejumlah buku tertentu, jawab hanya dengan jumlah tersebut tanpa tambahan.");
        messages.add(systemMessage);

        ObjectNode userMsg = objectMapper.createObjectNode();
        userMsg.put("role", "user");
        userMsg.put("content", userMessage);
        messages.add(userMsg);

        request.set("messages", messages);

        return webClient.post()
            .uri("/v1/chat/completions")
            .bodyValue(request.toString())
            .retrieve()
            .onStatus(status -> status.value() == 429, clientResponse ->
                Mono.error(new RuntimeException("⚠️ Batas permintaan Together AI tercapai. Silakan coba lagi nanti."))
            )
            .bodyToMono(JsonNode.class)
            .map(responseJson -> {
                System.out.println("📦 TogetherAI Response: " + responseJson.toPrettyString());
                String content = responseJson
                    .path("choices").path(0).path("message").path("content")
                    .asText("");

                // Bersihkan tag <think> jika ada
                content = content.replaceAll("(?s)<think>.*?</think>", "").trim();

                return new ChatResponse(content);
            });
    }
}
