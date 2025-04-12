package com.productservice.models;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.annotation.Id;

public class Product {
    @Id
    private int id;
    private String name;
    private String description;
    private BigDecimal price;

    public Product(String name, String description, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

}
