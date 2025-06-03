package com.rrt.rrtbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rrt.rrtbackend.entity.Product;
import com.rrt.rrtbackend.repository.ProductsRepository;

@Service
public class ProductsService {
    @Autowired
    private  ProductsRepository productsRepository;

    public List<Product> getAllProducts(){
        return productsRepository.findAll();
    }
    public List<Product> searchProductsByTitle(String title){
        return productsRepository.findByTitleContainingIgnoreCase(title);
    }
}
