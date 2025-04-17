package com.productservice.productservice.events;

import com.productservice.productservice.models.Product;

public class ProductDeletedEvent {
    public Product product;

    public ProductDeletedEvent(Product product) {
        this.product = product;
    }

}
