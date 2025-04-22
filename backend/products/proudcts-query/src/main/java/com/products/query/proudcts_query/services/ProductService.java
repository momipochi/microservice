package com.products.query.proudcts_query.services;

import org.springframework.stereotype.Service;

import com.products.query.proudcts_query.models.ProductDocument;
import com.products.query.proudcts_query.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Flux<ProductDocument> findAll() {
        return productRepository.findAll();
    }

    public Mono<ProductDocument> findById(int id) {
        return productRepository.findById(id);
    }

    public Mono<ProductDocument> findByName(String name) {
        return productRepository.findByName(name);
    }

}
