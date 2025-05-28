package com.productservice.productservice.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.productservice.productservice.dto.ProductRequest;
import com.productservice.productservice.dto.ProductResponse;
import com.productservice.productservice.dto.UpdateProductRequest;
import com.productservice.productservice.services.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Mono<ProductResponse>> createProduct(@RequestBody ProductRequest request) {
        try {
            return ResponseEntity.ok(productService.createProduct(request));
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Mono.error(new RuntimeException("Error creating product")));
        }
    }
    @PostMapping("/bulk")
    public ResponseEntity<Flux<ProductResponse>> createProducts(@RequestBody List<ProductRequest> requests) {
        Flux<ProductResponse> responses = Flux.fromIterable(requests)
                .flatMap(pr -> {
                    try {
                        return productService.createProduct(pr);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
        return ResponseEntity.ok(responses);
    }


    @PutMapping
    public Mono<ResponseEntity<ProductResponse>> updateProduct(@RequestBody UpdateProductRequest request) {
        return productService.updateProduct(request)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@PathVariable UUID id) {
        return productService.deleteProduct(id);
    }

}
