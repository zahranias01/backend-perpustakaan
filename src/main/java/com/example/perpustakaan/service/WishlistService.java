package com.example.perpustakaan.service;

import com.example.perpustakaan.model.Wishlist;
import com.example.perpustakaan.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    // Tambah wishlist, cek duplikat berdasarkan npm dan biblioId
    public String addWishlist(Wishlist wishlist) {
        boolean exists = wishlistRepository.existsByNpmAndBiblioId(wishlist.getNpm(), wishlist.getBiblioId());
        if (exists) {
            return "Buku sudah ada di bookmark";
        }
        wishlistRepository.save(wishlist);
        return "Berhasil menambahkan ke bookmark";
    }

    // Ambil wishlist user berdasar npm
    public List<Wishlist> getWishlistByNpm(String npm) {
        return wishlistRepository.findByNpm(npm);
    }

    // Update wishlist berdasar npm dan biblioId
    public boolean updateWishlist(String npm, int biblioId, Wishlist updatedWishlist) {
        Optional<Wishlist> optionalWishlist = wishlistRepository.findByNpmAndBiblioId(npm, biblioId);
        if (optionalWishlist.isPresent()) {
            Wishlist wishlist = optionalWishlist.get();
            // Update fields
            wishlist.setTitle(updatedWishlist.getTitle());
            wishlist.setCatatan(updatedWishlist.getCatatan());
            wishlist.setStatus(updatedWishlist.getStatus());
            wishlistRepository.save(wishlist);
            return true;
        } else {
            return false;
        }
    }

    // Hapus wishlist berdasar npm dan biblioId
    @Transactional
    public void deleteWishlist(String npm, int biblioId) {
        System.out.println(">> Hapus wishlist: npm=" + npm + ", biblioId=" + biblioId);
        boolean exists = wishlistRepository.existsByNpmAndBiblioId(npm, biblioId);
        System.out.println(">> Exists? " + exists);

        if (!exists) {
            throw new RuntimeException("Wishlist tidak ditemukan");
        }

        wishlistRepository.deleteByNpmAndBiblioId(npm, biblioId);
        System.out.println(">> Berhasil dihapus.");
    }
}