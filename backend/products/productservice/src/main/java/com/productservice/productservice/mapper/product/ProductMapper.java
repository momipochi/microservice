package com.productservice.productservice.mapper.product;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.microservice.events.products.ProductUpdatedEvent;
import com.productservice.productservice.dto.UpdateProductRequest;
import com.productservice.productservice.models.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    void updateProduct(UpdateProductRequest upr, @MappingTarget Product product);

    void toUpdateProductEvent(Product product, @MappingTarget ProductUpdatedEvent event);
}
