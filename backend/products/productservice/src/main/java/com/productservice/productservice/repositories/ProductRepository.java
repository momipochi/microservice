package com.productservice.productservice.repositories;

import java.util.UUID;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.productservice.productservice.domain.ProductDomain;

import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends R2dbcRepository<ProductDomain, UUID> {
    Mono<ProductDomain> findByAggregateId(UUID aggregateId);

    Mono<Boolean> existsByAggregateIdAndEventType(UUID aggregateId, String eventType);

    Mono<ProductDomain> findTopByAggregateIdAndEventTypeOrderByTimestampDesc(UUID aggregateId, String eventType);

    @Query("SELECT * FROM product_events WHERE aggregate_id = :aggregateId AND event_type != 'ProductDeletedEvent' ORDER BY timestamp DESC LIMIT 1")
    Mono<ProductDomain> findLatestNonDeletedEventByAggregateId(UUID aggregateId);
}
