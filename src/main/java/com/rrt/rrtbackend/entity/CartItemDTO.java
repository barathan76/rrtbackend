package com.rrt.rrtbackend.entity;

public class CartItemDTO {
    private Long id;
    private Product product;
    private int quantity;
    CartItemDTO(){}

    public CartItemDTO(long id, Product product, int quantity){
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public CartItemDTO(CartItem cartItem){
        this.id = cartItem.getId();
        this.product = cartItem.getProduct();
        this.quantity = cartItem.getQuantity();
    }

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

    
    
}
