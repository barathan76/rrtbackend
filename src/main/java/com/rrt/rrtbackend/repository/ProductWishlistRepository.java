package com.rrt.rrtbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rrt.rrtbackend.entity.product.Product;
import com.rrt.rrtbackend.entity.product.ProductWishlist;
import com.rrt.rrtbackend.entity.user.User;

public interface ProductWishlistRepository extends JpaRepository<ProductWishlist, Long> {
    List<ProductWishlist> findByUser(User user);
    Optional<ProductWishlist> findByUserAndProduct(User user, Product product);
    boolean existsByUserAndProduct(User user, Product product);
    void deleteByUserAndProduct(User user, Product product);
}

