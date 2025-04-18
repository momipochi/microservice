package com.productservice.productservice.events;

import com.productservice.productservice.models.Product;

public class ProductCreatedEvent {
    public Product product;

    public ProductCreatedEvent(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

}