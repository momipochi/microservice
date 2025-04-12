package com.productservice.repositories;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.productservice.models.Product;

import reactor.core.publisher.Mono;

public interface ProductRepository extends R2dbcRepository<Product, Integer> {

    Mono<Product> findByName(String name);
}
