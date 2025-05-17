package com.productservice.productservice.exceptions;

public class ProductHasAlreadyBeenDeletedException extends RuntimeException {
    public ProductHasAlreadyBeenDeletedException(String message) {
        super(message);
    }
}
