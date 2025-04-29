-- Enable UUID extension (run once per database)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE product_events (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    aggregate_id UUID NOT NULL DEFAULT uuid_generate_v4(),
    aggregate_type TEXT NOT NULL DEFAULT 'Product',
    event_type TEXT NOT NULL,
    data TEXT NOT NULL,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version INTEGER NOT NULL DEFAULT 1
);


INSERT INTO product_events (event_type, data)
VALUES 
('ProductCreated', '{"name":"Coffee Mug","price":12.99,"stock":100}'),
('ProductUpdated', '{"name":"Coffee Mug","price":10.99,"stock":120}');
