package com.rrt.rrtbackend.entity.order;

import java.time.LocalDateTime;
import java.util.Map;

public class OrderItemSummary {
    private String productName;
    private String productImg;
    private String category;
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getProductImg() {
        return productImg;
    }
    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }
    private double price;
    private int quantity;
    private OrderStatus currentStatus;
    private Map<OrderStatus,LocalDateTime> statusTimeStamps;

    public OrderItemSummary(){}
    public OrderItemSummary(String productImg, String productName, String category, double price, int quantity, OrderStatus currenStatus,
            Map<OrderStatus, LocalDateTime> statusTimeStamps) {
        this.productImg = productImg;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.currentStatus = currenStatus;
        this.statusTimeStamps = statusTimeStamps;
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public OrderStatus getCurrentStatus() {
        return currentStatus;
    }
    public void setCurrentStatus(OrderStatus currenStatus) {
        this.currentStatus = currenStatus;
    }
    public Map<OrderStatus, LocalDateTime> getStatusTimeStamps() {
        return statusTimeStamps;
    }
    public void setStatusTimeStamps(Map<OrderStatus, LocalDateTime> statusTimeStamps) {
        this.statusTimeStamps = statusTimeStamps;
    }
}
