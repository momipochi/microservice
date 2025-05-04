package com.microservice.events.products;

import java.util.UUID;

public class ProductDeletedEvent {
    private UUID productId;

        public UUID getProductId() {
            return productId;
        }

        public void setProductId(UUID productId) {
            this.productId = productId;
        }

}