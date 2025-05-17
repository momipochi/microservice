package com.productservice.productservice.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productservice.productservice.models.Product;

@Table("product_events")
public class ProductDomain {
    @Id
    private UUID id;
    private UUID aggregateId = UUID.randomUUID();
    private String aggregateType = "Product"; // e.g. "Product"
    private String eventType; // e.g. "ProductCreated"
    private String data; // This would be your Product object
    private LocalDateTime timestamp = LocalDateTime.now();
    private int version = 1; // optional

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAggregateId() {
        return aggregateId;
    }

    public void setAggregateId(UUID aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getAggregateType() {
        return aggregateType;
    }

    public void setAggregateType(String aggregateType) {
        this.aggregateType = aggregateType;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Product getDeserializedData() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Product result = mapper.readValue(data, Product.class);
        return result;
    }

}
