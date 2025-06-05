package com.rrt.rrtbackend.component;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rrt.rrtbackend.entity.product.Product;
import com.rrt.rrtbackend.repository.ProductsRepository;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private ProductsRepository productsRepository;
@Override
    public void run(String... args) throws Exception {
        if (productsRepository.count() == 0) {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Product>> typeReference = new TypeReference<>() {};
           
            InputStream inputStream = new ClassPathResource("data/products.json").getInputStream();
            List<Product> products = mapper.readValue(inputStream, typeReference);
            productsRepository.saveAll(products);
            System.out.println("Products loaded into database.");
        } else {
            System.out.println("Products already exist in database.");
        }
    }
}
