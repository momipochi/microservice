CREATE TABLE IF NOT EXISTS products (
  id UUID PRIMARY KEY,
  name TEXT NOT NULL,
  description TEXT,
  price NUMERIC(10, 2) NOT NULL
);

INSERT INTO products (id, name, description, price) VALUES
  (gen_random_uuid(), 'USB Cable', 'A durable USB-C cable', 9.99),
  (gen_random_uuid(), 'Wireless Mouse', 'Bluetooth-enabled mouse', 19.99);
