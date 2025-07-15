package com.example.perpustakaan.repository;

import com.example.perpustakaan.model.ChatbotHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatbotHistoryRepository extends JpaRepository<ChatbotHistory, Long> {
    List<ChatbotHistory> findByNpmOrderByCreatedAtDesc(String npm);
    List<ChatbotHistory> findTop5ByNpmOrderByCreatedAtDesc(String npm);

    @Transactional
    @Modifying
    void deleteAllByNpm(String npm);

}