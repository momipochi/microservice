package com.productservice.services;

import org.springframework.stereotype.Service;

import com.productservice.models.Product;
import com.productservice.repositories.ProductRepository;

import reactor.core.publisher.Mono;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    public Mono<Product> findById(int id) {
        return productRepository.findById(id);
    }

    public Mono<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

}
