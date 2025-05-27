package com.example.perpustakaan.model;

public class ChatResponse {
    private String reply;

    // Default constructor
    public ChatResponse() {
    }

    public ChatResponse(String reply) {
        this.reply = reply;
    }

    // Getter
    public String getReply() {
        return reply;
    }

    // Setter
    public void setReply(String reply) {
        this.reply = reply;
    }
}
