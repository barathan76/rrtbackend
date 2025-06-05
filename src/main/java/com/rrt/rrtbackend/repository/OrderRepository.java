package com.rrt.rrtbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rrt.rrtbackend.entity.order.Order;
import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
