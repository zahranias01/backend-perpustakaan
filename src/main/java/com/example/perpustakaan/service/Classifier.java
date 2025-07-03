package com.example.perpustakaan.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Classifier{

    // Daftar kata kunci untuk kategori lokal (harus sesuai dengan data di database)
    private final List<String> kategoriList = List.of(
        "informatika", "penerbangan", "akuntansi", "industri",
        "sejarah", "agama", "filsafat", "bisnis", "manajemen",
        "elektronika", "bahasa"
    );

    public String klasifikasikan(String message) {
        String msg = message.toLowerCase();

        // Deteksi jam buka
        if (msg.contains("jam buka") || msg.contains("jam operasional") || msg.contains("buka jam berapa")) {
            return "jam_buka";
        }

        // Deteksi pertanyaan yang menyebutkan kategori lokal
        for (String kategori : kategoriList) {
            if (msg.contains(kategori)) {
                return "lokal";
            }
        }

        // Default: online (kirim ke TogetherAI)
        return "online";
    }
}
