package com.rrt.rrtbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rrt.rrtbackend.entity.cart.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem,Long>{
    List<CartItem> findByUserId(Long userId);
    Optional<CartItem> findByUserIdAndProductId(Long userId,int productId);
    void deleteByUserIdAndProductId(Long userId,int productId);

    void deleteByUserId(Long userId);

}
