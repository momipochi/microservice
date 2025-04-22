package com.products.query.proudcts_query.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.products.query.proudcts_query.models.ProductDocument;

public interface ProductRepository extends MongoRepository<ProductDocument, Integer> {

}
