package com.rrt.rrtbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rrt.rrtbackend.entity.product.Product;
import com.rrt.rrtbackend.service.ProductWishlistService;

@RestController
@RequestMapping("/api/user/wishlist")
@CrossOrigin(origins = "*")
public class ProductWishlistController {

    @Autowired
    private ProductWishlistService wishlistService;

    @GetMapping
    public ResponseEntity<List<Product>> getWishlist(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(wishlistService.getWishlist(token.substring(7)));
    }

    @GetMapping("/check/{productId}")
    public ResponseEntity<Boolean> isWishlisted(@RequestHeader("Authorization") String token,
                                                 @PathVariable int productId) {
        return ResponseEntity.ok(wishlistService.isWishlisted(token.substring(7), productId));
    }

    @PostMapping("/toggle/{productId}")
    public ResponseEntity<String> toggleWishlist(@RequestHeader("Authorization") String token,
                                                 @PathVariable int productId) {
        wishlistService.toggleWishlist(token.substring(7), productId);
        return ResponseEntity.ok("Wishlist updated");
    }
}

