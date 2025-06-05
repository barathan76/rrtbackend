package com.rrt.rrtbackend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rrt.rrtbackend.entity.cart.CartItemRequest;
import com.rrt.rrtbackend.entity.order.Order;
import com.rrt.rrtbackend.entity.order.OrderItem;
import com.rrt.rrtbackend.entity.order.OrderItemSummary;
import com.rrt.rrtbackend.entity.order.OrderRequest;
import com.rrt.rrtbackend.entity.order.OrderResponse;
import com.rrt.rrtbackend.entity.order.OrderStatus;
import com.rrt.rrtbackend.entity.product.Product;
import com.rrt.rrtbackend.entity.user.User;
import com.rrt.rrtbackend.repository.OrderRepository;
import com.rrt.rrtbackend.repository.ProductsRepository;
import com.rrt.rrtbackend.repository.UserRepository;
import com.rrt.rrtbackend.utility.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class OrderService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductsRepository productsRepository;



    public User getUserFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtUtil.extractUserId(token);
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public OrderResponse placeOrder(User user, OrderRequest request){
        double totalAmount = 0;
        List<OrderItem> orderItems = new ArrayList<>();
        Order order = new Order();
        for(CartItemRequest itemRequest : request.getItems()){
            Product product = productsRepository.findById(itemRequest.getProductId()).orElseThrow(()->new RuntimeException("Product not found"));
            double price = product.getPrice() * itemRequest.getQuantity();
            totalAmount += price;
            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setPrice(price);
            item.setOrder(order);
            item.setQuantity(itemRequest.getQuantity());
            item.setCurrentStatus(OrderStatus.CONFIRMED);
            Map<OrderStatus, LocalDateTime> statusMap = new HashMap<>();
            statusMap.put(OrderStatus.CONFIRMED, LocalDateTime.now());
            item.setStatusTimeStamps(statusMap);
            orderItems.add(item);
        }        
        order.setAmount(totalAmount);
        order.setAddress(request.getAddress());
        order.setUser(user);
        order.setItems(orderItems);
        return convertOrderToResponse(orderRepository.save(order));
    }

    public OrderResponse convertOrderToResponse(Order order){
         OrderResponse orderResponse = new OrderResponse();
            orderResponse.setOrderId(order.getId());
            orderResponse.setAmount(order.getAmount());
            orderResponse.setAddress(order.getAddress());
            List<OrderItemSummary> items = order.getItems().stream().map(item -> {
                OrderItemSummary itemSummary = new OrderItemSummary();
                itemSummary.setProductImg(item.getProduct().getImageUrl());
                itemSummary.setCategory(item.getProduct().getCategory());
                itemSummary.setProductName(item.getProduct().getTitle());
                itemSummary.setPrice(item.getPrice());
                itemSummary.setQuantity(item.getQuantity());
                itemSummary.setCurrentStatus(item.getCurrentStatus());
                itemSummary.setStatusTimeStamps(item.getStatusTimeStamps());
                return itemSummary;
            }).collect(Collectors.toList());
        orderResponse.setItem(items);
        return orderResponse;
    }

    public List<OrderResponse> getUserOrders(User user){
        List<Order> orders = orderRepository.findByUserId(user.getId());
        return orders.stream().map(order -> {
            return convertOrderToResponse(order);
        }).collect(Collectors.toList());
    }

    



}
