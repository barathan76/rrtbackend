package com.rrt.rrtbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rrt.rrtbackend.entity.cart.CartItemDTO;
import com.rrt.rrtbackend.entity.cart.CartItemRequest;
import com.rrt.rrtbackend.service.CartService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user/cart")
public class CartItemController {
    
    @Autowired
    private CartService cartService;


    @GetMapping()
    public ResponseEntity<List<CartItemDTO>> getCartItems(HttpServletRequest request){
        Long userId = cartService.getUserFromRequest(request).getId();
        return ResponseEntity.ok(cartService.getCartItems(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<CartItemDTO> addItem(@RequestBody CartItemRequest cartItemRequest, HttpServletRequest request) {
        Long userId = cartService.getUserFromRequest(request).getId();
        
        return ResponseEntity.ok(cartService.addOrUpdateItem(userId, cartItemRequest.getProductId(),cartItemRequest.getQuantity()));
    }

    @PutMapping("/update")
    public ResponseEntity<CartItemDTO> updateItem(@RequestBody CartItemRequest cartItemRequest, HttpServletRequest request) {
        Long userId = cartService.getUserFromRequest(request).getId();
        return ResponseEntity.ok(cartService.addOrUpdateItem(userId, cartItemRequest.getProductId(), cartItemRequest.getQuantity()));
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> deleteItem(@PathVariable int productId,HttpServletRequest request) {
        System.out.println("Product Id " +productId );
        Long userId = cartService.getUserFromRequest(request).getId();
        cartService.removeItem(userId, productId);
        return ResponseEntity.ok("Item removed");
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(HttpServletRequest request){
        Long userId = cartService.getUserFromRequest(request).getId();
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared");
    }
    
    
}
