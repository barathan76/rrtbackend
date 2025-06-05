package com.rrt.rrtbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rrt.rrtbackend.entity.order.OrderRequest;
import com.rrt.rrtbackend.entity.order.OrderResponse;
import com.rrt.rrtbackend.entity.user.User;
import com.rrt.rrtbackend.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/user/order")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest, HttpServletRequest request) {
        User user = orderService.getUserFromRequest(request);
        OrderResponse response = orderService.placeOrder(user, orderRequest);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse>> getOrders(HttpServletRequest request) {
        User user = orderService.getUserFromRequest(request);
        return ResponseEntity.ok(orderService.getUserOrders(user));
    }
    
    
}
