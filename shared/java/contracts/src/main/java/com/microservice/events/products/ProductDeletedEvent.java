package com.microservice.events.products;

import java.util.UUID;

public class ProductDeletedEvent {
    public UUID productId;

    public ProductDeletedEvent(UUID id) {
        this.productId = id;
    }

}