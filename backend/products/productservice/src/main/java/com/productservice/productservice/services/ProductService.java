package com.productservice.productservice.services;

import java.util.Objects;
import java.util.UUID;

import com.productservice.productservice.exceptions.ProductHasAlreadyBeenDeletedException;
import com.productservice.productservice.exceptions.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.events.products.ProductCreatedEvent;
import com.microservice.events.products.ProductDeletedEvent;
import com.microservice.events.products.ProductUpdatedEvent;
import com.microservice.events.types.EventTypes;
import com.productservice.productservice.domain.ProductDomain;
import com.productservice.productservice.dto.ProductRequest;
import com.productservice.productservice.dto.ProductResponse;
import com.productservice.productservice.dto.UpdateProductRequest;
import com.productservice.productservice.mapper.product.ProductMapper;
import com.productservice.productservice.models.Product;
import com.productservice.productservice.repositories.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ProductMapper mapper;
    Logger logger = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepository productRepository, KafkaTemplate<String, Object> kafkaTemplate,
            ProductMapper mapper) {
        this.productRepository = productRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = mapper;
    }

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    public Flux<ProductResponse> findAll() {
        return productRepository.findAll().map(x -> {
            try {
                Product p = x.getDeserializedData();
                return new ProductResponse(x.getAggregateId(), p.getName(), p.getDescription(), p.getPrice());
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
            ProductCreatedEvent event = new ProductCreatedEvent();
            event.setId(saved.getAggregateId());
            event.setDescription(p.getDescription());
            event.setName(p.getName());
            event.setPrice(p.getPrice());
            kafkaTemplate.send(EventTypes.PRODUCT_CREATED_EVENT, saved.getAggregateId().toString(), event);
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
        return productRepository.findById(id)
                .flatMap(product -> productRepository.delete(product)
                        .doOnSuccess(unused -> {
                            ProductDeletedEvent event = new ProductDeletedEvent();
                            event.setProductId(id);
                            kafkaTemplate.send(EventTypes.PRODUCT_DELETED_EVENT, product.getId() + "",
                                    event);
                        }));
    }

    public Mono<ProductResponse> updateProduct(UpdateProductRequest request) {
        return productRepository.existsByAggregateIdAndEventType(request.id(), EventTypes.PRODUCT_DELETED_EVENT)
                .flatMap(isDeleted -> {
                    if (isDeleted) {
                        String msg = String.format("Cannot update. Product is deleted: %s", request.id());
                        logger.warn(msg);
                        return Mono.error(new ProductHasAlreadyBeenDeletedException(msg));
                    }

                    return productRepository.findLatestNonDeletedEventByAggregateId(request.id())
                            .switchIfEmpty(Mono.error(new ProductNotFoundException("No product found with id: " + request.id())))
                            .flatMap(existing -> updateExistingProduct(existing, request));
                });
    }

    private Mono<ProductResponse> updateExistingProduct(ProductDomain existing, UpdateProductRequest request) {
        Product product = safelyDeserialize(existing);
        mapper.updateProduct(request, product);

        logger.info("Updating product: {}", product.getName());

        ProductDomain updatedDomain = buildUpdatedProductDomain(existing.getAggregateId(), product);
        ProductUpdatedEvent event = buildUpdatedEvent(product, updatedDomain.getAggregateId());
        ProductResponse response = toResponse(updatedDomain.getAggregateId(), product);

        return productRepository.save(updatedDomain)
                .doOnSuccess(saved -> {
                    logger.info("Product saved with aggregateId: {}", saved.getAggregateId());
                    kafkaTemplate.send(EventTypes.PRODUCT_UPDATED_EVENT,
                            saved.getAggregateId().toString(), event);
                })
                .thenReturn(response);
    }

    private Product safelyDeserialize(ProductDomain domain) {
        try {
            return domain.getDeserializedData();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize product data", e);
        }
    }

    private ProductDomain buildUpdatedProductDomain(UUID aggregateId, Product product) {
        ProductDomain domain = new ProductDomain();
        domain.setAggregateId(aggregateId);
        domain.setEventType(EventTypes.PRODUCT_UPDATED_EVENT);
        try {
            domain.setData(product.serialize());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize product", e);
        }
        return domain;
    }

    private ProductUpdatedEvent buildUpdatedEvent(Product product, UUID aggregateId) {
        ProductUpdatedEvent event = new ProductUpdatedEvent();
        event.setId(aggregateId);
        mapper.toUpdateProductEvent(product, event);
        return event;
    }


    private ProductResponse toResponse(ProductDomain domain) throws JsonProcessingException {
        Product product = domain.getDeserializedData();
        return new ProductResponse(domain.getAggregateId(), product.getName(), product.getDescription(),
                product.getPrice());
    }

    private ProductResponse toResponse(UUID aggregateId, Product product) {
        logger.info(String.format("Name: %s, Description: %s, Price: %s", product.getName(), product.getDescription(),
                product.getPrice().toString()));
        return new ProductResponse(aggregateId, product.getName(), product.getDescription(),
                product.getPrice());
    }

}
