package com.products.query.proudcts_query.models;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "products")
public class ProductDocument {
    @Id
    private int id;
    private String name;
    private String description;
    private BigDecimal price;

}
