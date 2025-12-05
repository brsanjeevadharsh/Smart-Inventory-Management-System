CREATE DATABASE inventory_simple;

USE inventory_simple;

CREATE TABLE products (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  quantity INT,
  price DOUBLE
);
