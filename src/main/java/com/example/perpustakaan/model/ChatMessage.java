package com.example.perpustakaan.model;

public class ChatMessage {
    private String message;

    // Default constructor (penting untuk deserialisasi JSON)
    public ChatMessage() {
    }

    public ChatMessage(String message) {
        this.message = message;
    }

    // Getter
    public String getMessage() {
        return message;
    }

    // Setter
    public void setMessage(String message) {
        this.message = message;
    }
}
