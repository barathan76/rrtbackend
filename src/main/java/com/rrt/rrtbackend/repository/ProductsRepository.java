package com.rrt.rrtbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rrt.rrtbackend.entity.product.Product;

public interface ProductsRepository extends JpaRepository<Product,Integer>{
    List<Product> findByTitleContainingIgnoreCase(String title);

}
