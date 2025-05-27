package com.example.perpustakaan.service;

import com.example.perpustakaan.model.Wishlist;
import com.example.perpustakaan.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    // Simpan wishlist jika belum ada
    public String addWishlist(Wishlist wishlist) {
        boolean exists = wishlistRepository.existsByNpmAndBiblioId(wishlist.getNpm(), wishlist.getBiblioId());
        if (exists) {
            return "Buku sudah ada di bookmark";
        }
        wishlistRepository.save(wishlist);
        return "Berhasil menambahkan ke bookmark";
    }

    // Ambil daftar wishlist user berdasarkan npm
    public List<Wishlist> getWishlistByNpm(String npm) {
        return wishlistRepository.findByNpm(npm);
    }
}
