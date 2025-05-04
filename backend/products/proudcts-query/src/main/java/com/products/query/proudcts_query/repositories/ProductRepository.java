package com.products.query.proudcts_query.repositories;

import java.util.UUID;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.products.query.proudcts_query.models.ProductDocument;

import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<ProductDocument, UUID> {
    Mono<ProductDocument> findByName(String name);
}
