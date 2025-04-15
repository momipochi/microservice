CREATE TABLE IF NOT EXISTS products (
  id SERIAL PRIMARY KEY, -- Use SERIAL for auto-incrementing integer
  name TEXT NOT NULL,
  description TEXT,
  price NUMERIC(10, 2) NOT NULL
);

INSERT INTO products (name, description, price) VALUES
  ('USB Cable', 'A durable USB-C cable', 9.99),
  ('Wireless Mouse', 'Bluetooth-enabled mouse', 19.99);