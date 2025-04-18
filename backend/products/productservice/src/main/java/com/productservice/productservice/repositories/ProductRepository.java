package com.productservice.productservice.repositories;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.productservice.productservice.models.Product;

import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends R2dbcRepository<Product, Integer> {

    Mono<Product> findByName(String name);
}
