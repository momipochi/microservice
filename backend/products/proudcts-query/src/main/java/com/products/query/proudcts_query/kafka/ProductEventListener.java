package com.products.query.proudcts_query.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.microservice.events.products.ProductCreatedEvent;
import com.microservice.events.products.ProductUpdatedEvent;
import com.microservice.events.types.EventTypes;
import com.products.query.proudcts_query.models.ProductDocument;
import com.products.query.proudcts_query.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductEventListener {

    private final ProductRepository productRepository;
    private final Logger logger = LoggerFactory.getLogger(ProductEventListener.class);

    @KafkaListener(topics = EventTypes.PRODUCT_CREATED_EVENT, groupId = "${spring.kafka.consumer.group-id}", containerFactory = "kafkaListenerContainerFactory")
    public void handleProductCreated(ProductCreatedEvent event) {
        ProductDocument doc = new ProductDocument();
        doc.setId(event.getId());
        doc.setName(event.getName());
        doc.setDescription(event.getDescription());
        doc.setPrice(event.getPrice());

        productRepository.save(doc).subscribe();
    }

    @KafkaListener(topics = EventTypes.PRODUCT_UPDATED_EVENT, groupId = "${spring.kafka.consumer.group-id}", containerFactory = "productUpdatedContainerFactory")
    public void handleProductUpdated(ProductUpdatedEvent event) {
        productRepository.findById(event.getId())
                .defaultIfEmpty(new ProductDocument()) // Create a new document if none exists
                .map(existingDoc -> {
                    existingDoc.setName(event.getName());
                    existingDoc.setDescription(event.getDescription());
                    existingDoc.setPrice(event.getPrice());
                    return existingDoc;
                })
                .flatMap(productRepository::save)
                .doOnSuccess(saved -> logger.info("Saved product to MongoDB: {}", saved))
                .doOnError(error -> logger.error("Failed to save product to MongoDB", error))
                .subscribe(); // Trigger the reactive pipeline
    }
}
