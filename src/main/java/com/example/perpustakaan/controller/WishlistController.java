package com.example.perpustakaan.controller;

import com.example.perpustakaan.model.Wishlist;
import com.example.perpustakaan.service.WishlistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmark")
@CrossOrigin(origins = {"http://localhost:5173", "https://nurtanio-perpustakaan.netlify.app"}, allowCredentials = "true")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    // Helper method untuk cek autentikasi & otorisasi berdasarkan npm
    private ResponseEntity<String> checkAuthenticationAndAuthorization(String npm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            System.out.println("[AuthCheck] User not authenticated");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Silakan login terlebih dahulu");
        }
        String loggedInNpm = authentication.getName();
        System.out.println("[AuthCheck] Logged in npm: " + loggedInNpm);
        
        if (!npm.equals(loggedInNpm)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Akses ditolak");
        }
        return null; // valid
    }

    // CREATE
    @PostMapping("/{npm}")
    public ResponseEntity<?> addWishlist(@PathVariable String npm, @RequestBody Wishlist wishlist) {
        ResponseEntity<String> authCheck = checkAuthenticationAndAuthorization(npm);
        if (authCheck != null) return authCheck;

        if (!npm.equals(wishlist.getNpm())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("NPM di body tidak cocok");
        }

        String message = wishlistService.addWishlist(wishlist);

        if (message.equals("Buku sudah ada di bookmark")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);  // Status 409: conflict
        }

        return ResponseEntity.ok(message);
    }

    // READ
    @GetMapping("/{npm}")
    public ResponseEntity<?> getWishlistByNpm(@PathVariable String npm) {
        ResponseEntity<String> authCheck = checkAuthenticationAndAuthorization(npm);
        if (authCheck != null) return authCheck;

        List<Wishlist> list = wishlistService.getWishlistByNpm(npm);
        return ResponseEntity.ok(list);
    }

    // UPDATE
    @PutMapping("/{npm}/{biblioId}")
    public ResponseEntity<?> updateWishlist(@PathVariable String npm,
                                            @PathVariable int biblioId,
                                            @RequestBody Wishlist updatedWishlist) {
        ResponseEntity<String> authCheck = checkAuthenticationAndAuthorization(npm);
        if (authCheck != null) return authCheck;

        boolean updated = wishlistService.updateWishlist(npm, biblioId, updatedWishlist);
        if (updated) {
            return ResponseEntity.ok("Wishlist berhasil diperbarui");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wishlist tidak ditemukan");
        }
    }

    // DELETE
    @DeleteMapping("/{npm}/{biblioId}")
    public ResponseEntity<?> deleteBookmark(@PathVariable String npm,
                                            @PathVariable int biblioId) {
        ResponseEntity<String> authCheck = checkAuthenticationAndAuthorization(npm);
        if (authCheck != null) return authCheck;

        try {
            wishlistService.deleteWishlist(npm, biblioId);
            return ResponseEntity.ok("Bookmark berhasil dihapus");
        } catch (Exception e) {
            e.printStackTrace();
            if ("Wishlist tidak ditemukan".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal menghapus bookmark: " + e.getMessage());
        }
    }
}