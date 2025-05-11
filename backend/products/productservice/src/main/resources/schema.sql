-- Enable UUID extension (run once per database)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS product_events (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    aggregate_id UUID NOT NULL DEFAULT uuid_generate_v4(),
    aggregate_type TEXT NOT NULL DEFAULT 'Product',
    event_type TEXT NOT NULL,
    data TEXT NOT NULL,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version INTEGER NOT NULL DEFAULT 1
);

-- Insert products only if table is empty
INSERT INTO product_events (aggregate_id, event_type, data)
SELECT uuid_generate_v4(), 'ProductCreated', data::TEXT
FROM (
    SELECT '{"name":"Laptop","description":"Gaming Laptop","price":1200.00}' AS data
    UNION ALL SELECT '{"name":"Phone","description":"Smartphone with 128GB","price":799.99}'
    UNION ALL SELECT '{"name":"Monitor","description":"27-inch 4K UHD display","price":350.00}'
    UNION ALL SELECT '{"name":"Keyboard","description":"Mechanical keyboard with RGB","price":89.99}'
    UNION ALL SELECT '{"name":"Mouse","description":"Wireless ergonomic mouse","price":49.50}'
    UNION ALL SELECT '{"name":"Tablet","description":"10-inch tablet with stylus","price":299.99}'
) AS sub
WHERE NOT EXISTS (SELECT 1 FROM product_events);
