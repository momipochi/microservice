package com.products.query.proudcts_query.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.products.query.proudcts_query.events.ProductCreatedEvent;
import com.products.query.proudcts_query.models.ProductDocument;
import com.products.query.proudcts_query.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductEventListener {

    private final ProductRepository productRepository;

    @KafkaListener(topics = KafkaConsumerConfig.PRODUCT_CREATED_TOPIC, groupId = "${spring.kafka.consumer.group-id}", containerFactory = "kafkaListenerContainerFactory")
    public void handleProductCreated(ProductCreatedEvent event) {
        ProductDocument doc = new ProductDocument();
        doc.setId(event.getId());
        doc.setName(event.getName());
        doc.setDescription(event.getDescription());
        doc.setPrice(event.getPrice());

        productRepository.save(doc);
    }
}
