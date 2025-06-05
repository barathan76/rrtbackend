package com.rrt.rrtbackend.entity.order;

import java.util.List;

import com.rrt.rrtbackend.entity.user.UserAddress;

public class OrderResponse {
    private Long orderId;
    private double amount;
    private UserAddress address;
    private List<OrderItemSummary> item;
    public OrderResponse(){}
    public OrderResponse(Long orderId, double amount, UserAddress address, List<OrderItemSummary> item) {
        this.orderId = orderId;
        this.amount = amount;
        this.address = address;
        this.item = item;
    }
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public UserAddress getAddress() {
        return address;
    }
    public void setAddress(UserAddress address) {
        this.address = address;
    }
    public List<OrderItemSummary> getItem() {
        return item;
    }
    public void setItem(List<OrderItemSummary> item) {
        this.item = item;
    }
}
