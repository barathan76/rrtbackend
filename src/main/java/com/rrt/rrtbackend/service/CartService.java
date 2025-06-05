package com.rrt.rrtbackend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rrt.rrtbackend.entity.cart.CartItem;
import com.rrt.rrtbackend.entity.cart.CartItemDTO;
import com.rrt.rrtbackend.entity.product.Product;
import com.rrt.rrtbackend.entity.user.User;
import com.rrt.rrtbackend.repository.CartItemRepository;
import com.rrt.rrtbackend.repository.ProductsRepository;
import com.rrt.rrtbackend.repository.UserRepository;
import com.rrt.rrtbackend.utility.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class CartService {

@Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductsRepository productsRepository;

    public User getUserFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtUtil.extractUserId(token);
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public CartItemDTO addOrUpdateItem(Long userId,int productId, int quantity){
        Optional<CartItem> existing  = cartItemRepository.findByUserIdAndProductId(userId,productId);
        Product product = productsRepository.findById(productId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        CartItem item = existing.orElse(new CartItem());
        item.setUser(user);
        item.setProduct(product);
        item.setQuantity(quantity);
        return new CartItemDTO(cartItemRepository.save(item));
    }
    @Transactional
    public void removeItem(Long userId,int productId){
        cartItemRepository.deleteByUserIdAndProductId(userId,productId);
    }

    public List<CartItemDTO> getCartItems(Long userId){
        return cartItemRepository.findByUserId(userId).stream().map(CartItemDTO :: new).collect(Collectors.toList());
    }

    @Transactional
    public void clearCart(Long userId){
        System.err.println("Clear cart called");
        cartItemRepository.deleteByUserId(userId);
    }




}
