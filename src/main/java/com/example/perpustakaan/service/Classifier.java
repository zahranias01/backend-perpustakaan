package com.example.perpustakaan.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Classifier{

    // Daftar kata kunci untuk kategori lokal (harus sesuai dengan data di database)
    private final List<String> kategoriList = List.of(
     "informatika", "penerbangan", "akuntansi", "industri",
            "sejarah", "agama", "filsafat", "bisnis", "manajemen",
            "elektronika", "bahasa","sistem","computer","komputer","pemrograman","informasi","aircraft","flight","teknik","pajak","ekonomi","kamus"
    );

    public String klasifikasikan(String message) {
        String msg = message.toLowerCase();

        if (msg.contains("jam buka") || msg.contains("jam operasional") || msg.contains("buka jam berapa")) {
            return "jam_buka";
        }

        boolean containsKategori = false;
        for (String kategori : kategoriList) {
            if (msg.contains(kategori)) {
                containsKategori = true;
                break;
            }
        }

        if (containsKategori && msg.contains("luar")) {
            return "online"; // Jika ada kata 'luar', ambil dari luar (online)
        }

        if (containsKategori) {
            return "lokal";
        }

        return "online";
    }

}
