package com.rrt.rrtbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rrt.rrtbackend.entity.Product;

public interface ProductsRepository extends JpaRepository<Product,Integer>{
    

}
