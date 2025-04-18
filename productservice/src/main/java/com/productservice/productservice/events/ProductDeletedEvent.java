package com.productservice.productservice.events;

public class ProductDeletedEvent {
    public int productId;

    public ProductDeletedEvent(int id) {
        this.productId = id;
    }

}
