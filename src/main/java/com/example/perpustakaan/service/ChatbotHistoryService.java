package com.example.perpustakaan.service;

import com.example.perpustakaan.model.ChatbotHistory;
import com.example.perpustakaan.repository.ChatbotHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatbotHistoryService {

    @Autowired
    private ChatbotHistoryRepository repository;

    public List<ChatbotHistory> getAllChatbotHistories() {
        return repository.findAll();
    }

    public Optional<ChatbotHistory> getChatbotHistoryById(Long id) {
        return repository.findById(id);
    }

    public List<ChatbotHistory> getChatbotHistoryByNpm(String npm) {
        return repository.findByNpmOrderByCreatedAtDesc(npm);
    }

    public ChatbotHistory saveChatbotHistory(ChatbotHistory chatbotHistory) {
        return repository.save(chatbotHistory);
    }

    public void deleteChatbotHistory(Long id) {
        repository.deleteById(id);
    }

    public void deleteAllByNpm(String npm) {
        repository.deleteAllByNpm(npm);
    }
}
