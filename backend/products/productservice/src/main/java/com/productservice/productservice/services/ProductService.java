package com.productservice.productservice.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.productservice.productservice.dto.ProductRequest;
import com.productservice.productservice.dto.ProductResponse;
import com.productservice.productservice.events.ProductCreatedEvent;
import com.productservice.productservice.events.ProductDeletedEvent;
import com.productservice.productservice.kafka.KafkaProducerConfig;
import com.productservice.productservice.models.Product;
import com.productservice.productservice.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    public Flux<Product> findAll() {
        return productRepository.findAll();
    }

    public Mono<Product> findById(int id) {
        return productRepository.findById(id);
    }

    public Mono<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    public Mono<ProductResponse> createProduct(ProductRequest pr) {
        Product p = new Product(pr.name(), pr.description(), pr.price());
        return productRepository.save(p).doOnSuccess(saved -> {
            ProductCreatedEvent event = new ProductCreatedEvent(p);
            kafkaTemplate.send(KafkaProducerConfig.PRODUCT_CREATED_TOPIC, p.getId() + "", event);
        }).map(this::toResponse);
    }

    public Mono<Void> deleteProduct(int id) {
        return productRepository.findById(id)
                .flatMap(product -> productRepository.delete(product)
                        .doOnSuccess(unused -> {
                            ProductDeletedEvent event = new ProductDeletedEvent(product.getId());
                            kafkaTemplate.send(KafkaProducerConfig.PRODUCT_DELETED_TOPIC, product.getId() + "", event);
                        }));
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }
}
