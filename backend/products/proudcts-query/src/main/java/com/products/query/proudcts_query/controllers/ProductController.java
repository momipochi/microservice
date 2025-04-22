package com.products.query.proudcts_query.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.products.query.proudcts_query.models.ProductDocument;
import com.products.query.proudcts_query.services.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ProductDocument> getProductByName(@RequestParam(required = false) String name) {
        if (name == null) {
            return productService.findAll();
        }
        return productService.findByName(name).flux();
    }

    @GetMapping("/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductDocument> getProductById(@PathVariable("id") String id) {
        return productService.findById(Integer.parseInt(id));
    }
}
