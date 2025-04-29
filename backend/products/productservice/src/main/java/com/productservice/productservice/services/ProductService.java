package com.productservice.productservice.services;

import java.util.Objects;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productservice.productservice.constants.EventTypes;
import com.productservice.productservice.domain.ProductDomain;
import com.productservice.productservice.dto.ProductRequest;
import com.productservice.productservice.dto.ProductResponse;
import com.productservice.productservice.events.ProductCreatedEvent;
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
    Logger logger = LoggerFactory.getLogger(ProductService.class);

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    public Flux<Product> findAll() {
        return productRepository.findAll().map(x -> {
            try {
                return x.getDeserializedData();
            } catch (JsonProcessingException e) {
                logger.error("Error deserializing data for domain: {}", x.getId(), e);
                return null;
            }
        }).filter(Objects::nonNull).doOnError(e -> {
            logger.error("Error in the Flux stream: ", e);
        }).onErrorResume(e -> Flux.empty());
    }

    public Mono<Product> findById(UUID id) {
        return productRepository.findById(id).map(x -> {
            try {
                return x.getDeserializedData();
            } catch (JsonProcessingException e) {
                logger.error("Error deserializing data for domain: {}", x.getId(), e);
                return null;
            }
        });
    }

    public Mono<ProductResponse> createProduct(ProductRequest pr) throws JsonProcessingException {
        Product p = new Product(pr.name(), pr.description(), pr.price());
        ObjectMapper mapper = new ObjectMapper();
        String productJsonString = mapper.writeValueAsString(p);
        ProductDomain pd = new ProductDomain();
        pd.setData(productJsonString);
        pd.setEventType(EventTypes.PRODUCT_CREATED_EVENT);
        return productRepository.save(pd).doOnSuccess(saved -> {
            ProductCreatedEvent event = new ProductCreatedEvent(saved.getAggregateId(), p.getName(), p.getDescription(),
                    p.getPrice());
            kafkaTemplate.send(EventTypes.PRODUCT_CREATED_EVENT, saved.getAggregateId() + "", event);
        }).map(t -> {
            try {
                return toResponse(t);
            } catch (JsonProcessingException e) {
                logger.error("Error converting domain to Product response.", e);
                return null;
            }
        });
    }

    public Mono<Void> deleteProduct(UUID id) {
        return null;
        // return productRepository.findById(id)
        // .flatMap(product -> productRepository.delete(product)
        // .doOnSuccess(unused -> {
        // ProductDeletedEvent event = new ProductDeletedEvent(product.getId());
        // kafkaTemplate.send(EventTypes.PRODUCT_DELETED_EVENT, product.getId() + "",
        // event);
        // }));
    }

    private ProductResponse toResponse(ProductDomain domain) throws JsonMappingException, JsonProcessingException {
        Product product = domain.getDeserializedData();
        return new ProductResponse(domain.getAggregateId(), product.getName(), product.getDescription(),
                product.getPrice());
    }
}
