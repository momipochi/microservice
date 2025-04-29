package com.productservice.productservice.repositories;

import java.util.UUID;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.productservice.productservice.domain.ProductDomain;

@Repository
public interface ProductRepository extends R2dbcRepository<ProductDomain, UUID> {

}
