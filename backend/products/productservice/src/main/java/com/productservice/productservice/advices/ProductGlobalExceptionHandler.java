package com.productservice.productservice.advices;

import com.productservice.productservice.exceptions.ProductHasAlreadyBeenDeletedException;
import com.productservice.productservice.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestControllerAdvice
public class ProductGlobalExceptionHandler {
    @ExceptionHandler(ProductHasAlreadyBeenDeletedException.class)
    public Mono<ResponseEntity<Map<String, String>>> handleProductHasAlreadyBeenDeleted(ProductHasAlreadyBeenDeletedException ex) {
        return Mono.just(
                ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", ex.getMessage()))
        );
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public Mono<ResponseEntity<Map<String, String>>> handleProductNotFound(ProductNotFoundException ex) {
        return Mono.just(
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()))
        );
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<Map<String, String>>> handleGlobalException(Exception ex) {
        return Mono.just(
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", ex.getMessage()))
        );
    }
    @ExceptionHandler(RuntimeException.class)
    public Mono<ResponseEntity<Map<String, String>>> handleGlobalRuntimeException(RuntimeException ex) {
        return Mono.just(
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", ex.getMessage()))
        );
    }
}
