package com.rrt.rrtbackend.entity.order;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.rrt.rrtbackend.entity.product.Product;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.MapKeyEnumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;
    private int quantity;
    private double price;

    @Enumerated(EnumType.STRING)
    private OrderStatus currentStatus;

    @ElementCollection
    @CollectionTable(name = "order_status_timestamps", joinColumns = @JoinColumn(name = "order_item_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "timestamp")
    private Map<OrderStatus,LocalDateTime> statusTimeStamps = new HashMap<>();


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public Product getProduct() {
        return product;
    }


    public void setProduct(Product product) {
        this.product = product;
    }


    public int getQuantity() {
        return quantity;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public double getPrice() {
        return price;
    }


    public void setPrice(double price) {
        this.price = price;
    }


    public OrderStatus getCurrentStatus() {
        return currentStatus;
    }


    public void setCurrentStatus(OrderStatus currStatus) {
        this.currentStatus = currStatus;
    }


    public Map<OrderStatus, LocalDateTime> getStatusTimeStamps() {
        return statusTimeStamps;
    }


    public void setStatusTimeStamps(Map<OrderStatus, LocalDateTime> statusTimeStamps) {
        this.statusTimeStamps = statusTimeStamps;
    }


    public Order getOrder() {
        return order;
    }


    public void setOrder(Order order) {
        this.order = order;
    }
    
}
