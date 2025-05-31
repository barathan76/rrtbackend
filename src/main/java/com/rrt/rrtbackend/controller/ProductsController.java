package com.rrt.rrtbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rrt.rrtbackend.entity.Product;
import com.rrt.rrtbackend.service.ProductsService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/products")
public class ProductsController {
    @Autowired
    private ProductsService productsService;

    @GetMapping("/get_products")
    public List<Product> getProducts() {
        return productsService.getAllProducts();
    }

}
