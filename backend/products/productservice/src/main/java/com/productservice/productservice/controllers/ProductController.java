package com.productservice.productservice.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.productservice.productservice.dto.ProductRequest;
import com.productservice.productservice.dto.ProductResponse;
import com.productservice.productservice.services.ProductService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    // @GetMapping("/products")
    // @ResponseStatus(HttpStatus.OK)
    // public Flux<Product> getProductByName(@RequestParam(required = false) String
    // name) {
    // if (name == null) {
    // return productService.findAll();
    // }
    // return productService.findByName(name).flux();
    // }

    // @GetMapping("/products/{id}")
    // @ResponseStatus(HttpStatus.OK)
    // public Mono<Product> getProductById(@PathVariable("id") String id) {
    // return productService.getProductRepository().findById(Integer.parseInt(id));
    // }

    @PostMapping
    public ResponseEntity<Mono<ProductResponse>> createProduct(@RequestBody ProductRequest request) {
        try {
            return ResponseEntity.ok(productService.createProduct(request));
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Mono.error(new RuntimeException("Error creating product")));
        }
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@PathVariable UUID id) {
        return productService.deleteProduct(id);
    }

}
