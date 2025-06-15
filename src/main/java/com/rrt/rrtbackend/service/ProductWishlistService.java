package com.rrt.rrtbackend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rrt.rrtbackend.entity.product.Product;
import com.rrt.rrtbackend.entity.product.ProductWishlist;
import com.rrt.rrtbackend.entity.user.User;
import com.rrt.rrtbackend.repository.ProductWishlistRepository;
import com.rrt.rrtbackend.repository.ProductsRepository;
import com.rrt.rrtbackend.repository.UserRepository;
import com.rrt.rrtbackend.utility.JwtUtil;

@Service
public class ProductWishlistService {

    @Autowired
    private ProductWishlistRepository wishlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public User getUserFromToken(String token) {
        Long userId = jwtUtil.extractUserId(token);
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<Product> getWishlist(String token) {
        User user = getUserFromToken(token);
        return wishlistRepository.findByUser(user).stream()
                .map(ProductWishlist::getProduct)
                .collect(Collectors.toList());
    }

    public boolean isWishlisted(String token, int productId) {
        System.err.println("isWishlist is called");
        User user = getUserFromToken(token);
        Product product = productsRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return wishlistRepository.existsByUserAndProduct(user, product);
    }

    public void toggleWishlist(String token, int productId) {
        System.err.println("toggleWishlist is called");
        User user = getUserFromToken(token);
        Product product = productsRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<ProductWishlist> existing = wishlistRepository.findByUserAndProduct(user, product);
        if (existing.isPresent()) {
            System.err.println("already exists");
            wishlistRepository.delete(existing.get());
        } else {
            ProductWishlist wishlist = new ProductWishlist();
            wishlist.setUser(user);
            wishlist.setProduct(product);
            wishlistRepository.save(wishlist);
            System.err.println("item added to wishlist");
        }
    }
}

