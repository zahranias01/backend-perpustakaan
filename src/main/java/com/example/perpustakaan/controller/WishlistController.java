package com.example.perpustakaan.controller;

import com.example.perpustakaan.model.Wishlist;
import com.example.perpustakaan.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmark")
@CrossOrigin(origins = "http://localhost:5173")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/{npm}")
    public ResponseEntity<?> addWishlist(@PathVariable String npm, @RequestBody Wishlist wishlist) {
        // Validasi npm di path harus sama dengan npm di request body
        if (!npm.equals(wishlist.getNpm())) {
            return ResponseEntity.status(403).body("NPM mismatch antara URL dan data body");
        }

        String result = wishlistService.addWishlist(wishlist);
        if (result.equals("Buku sudah ada di bookmark")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{npm}")
    public List<Wishlist> getWishlistByNpm(@PathVariable String npm) {
        return wishlistService.getWishlistByNpm(npm);
    }
}