package com.rrt.rrtbackend.entity.order;

import java.util.List;

import com.rrt.rrtbackend.entity.cart.CartItemRequest;
import com.rrt.rrtbackend.entity.user.UserAddress;

public class OrderRequest {
    private List<CartItemRequest> items;
    private UserAddress address;
    public OrderRequest(){}
    public OrderRequest(List<CartItemRequest> items, UserAddress address) {
        this.items = items;
        this.address = address;
    }
    public List<CartItemRequest> getItems() {
        return items;
    }
    public void setItems(List<CartItemRequest> items) {
        this.items = items;
    }
    public UserAddress getAddress() {
        return address;
    }
    public void setAddress(UserAddress address) {
        this.address = address;
    }
}
