package com.example.perpustakaan.service;

import com.example.perpustakaan.model.ChatResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TogetherAIService {
    private WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Value("${together.api.key}") private String apiKey;
    @Value("${together.api.url}") private String apiUrl;
    @Value("${together.api.model}") private String model;
    @Autowired private WebClient.Builder builder;
    @PostConstruct
    public void init() {
        this.webClient = builder.baseUrl(apiUrl).build();
    }
    public Mono<ChatResponse> getChatCompletion(String userMessage) {
        ObjectNode req = objectMapper.createObjectNode();
        req.put("model", model);
        ArrayNode arr = objectMapper.createArrayNode();
        ObjectNode sys = objectMapper.createObjectNode();
        sys.put("role", "system");
        sys.put("content", "Anda adalah asisten perpustakaan yang tahu semua buku, berpengetahuan luas, harus memakai Bahasa Indonesia (selalu pakai) dan juga jangan balas chat selain tentang buku, perpustakaan, rekomendasi buku dan yang tidak berbau buku.");
        arr.add(sys);
        ObjectNode user = objectMapper.createObjectNode();
        user.put("role", "user");
        user.put("content", userMessage);
        arr.add(user);
        req.set("messages", arr);
        req.put("max_tokens", 512);
        req.put("temperature", 0.7);
        return webClient.post()
            .uri("/v1/chat/completions")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(req.toString())
            .retrieve()
            .bodyToMono(JsonNode.class)
            .map(res -> {
                String reply = res.path("choices").path(0).path("message").path("content").asText();
                reply = reply.replaceAll("<think>[\\s\\S]*?<\\/think>\\s*", "").trim();
                return new ChatResponse(reply);
            });
    }
}
