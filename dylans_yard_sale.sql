DROP DATABASE IF EXISTS dylans_yard_sale; 
CREATE DATABASE dylans_yard_sale;
USE dylans_yard_sale;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS product_tags;
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS tags;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE products (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(1000) NULL,
  price DECIMAL(10,2) NOT NULL,
  category ENUM('CLOTHING','RECORD','COMIC') NOT NULL,
  genre VARCHAR(255) NULL,
  PRIMARY KEY (id)
);

CREATE TABLE tags (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_tags_name (name)
);

CREATE TABLE product_tags (
  product_id BIGINT NOT NULL,
  tag_id BIGINT NOT NULL,
  PRIMARY KEY (product_id, tag_id),
  CONSTRAINT fk_product_tags_product
    FOREIGN KEY (product_id) REFERENCES products(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_product_tags_tag
    FOREIGN KEY (tag_id) REFERENCES tags(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
--Many-to-many bridge table between products and tags.

CREATE TABLE orders (
  id BIGINT NOT NULL AUTO_INCREMENT,
  order_date DATETIME NOT NULL,
  packaging_cost DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  status ENUM('PROCESSING','SHIPPED','COMPLETED') NOT NULL,
  PRIMARY KEY (id),
  INDEX idx_orders_status (status)
);

CREATE TABLE order_items (
  id BIGINT NOT NULL AUTO_INCREMENT,
  order_id BIGINT NOT NULL,
  product_id BIGINT NOT NULL,
  quantity INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT chk_order_items_quantity CHECK (quantity > 0),
  CONSTRAINT fk_order_items_order
    FOREIGN KEY (order_id) REFERENCES orders(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_order_items_product
    FOREIGN KEY (product_id) REFERENCES products(id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);
--One-to-many modeled as orders -> order_items.

INSERT INTO tags (name) VALUES
('Vintage'),
('Stone Island'),
('Band Tees'),
('Reggae'),
('Punk'),
('Metal'),
('Hip-Hop'),
('DC'),
('Image');

INSERT INTO products (name, description, price, category, genre) VALUES
('Vintage Burberry Baseball Hat', '', 45.00, 'CLOTHING', NULL),
('Stone Island Black Cargo Pants', '', 98.00, 'CLOTHING', NULL),
('Melvins 1993 Houdini Tour T-Shirt', '', 52.00, 'CLOTHING', NULL),
('Desmond Dekker / Aces - 007 Shanty Town', '', 42.00, 'RECORD', 'Reggae'),
('Sugar Minott / Rydim', '', 32.00, 'RECORD', 'Reggae'),
('Negative Approach / Tied Down', '', 25.00, 'RECORD', 'Punk'),
('Blitz / Voice of a Generation', '', 27.00, 'RECORD', 'Punk'),
('Acid Bath / When the Kite String Pops', '', 65.00, 'RECORD', 'Metal'),
('PeelingFlesh / G-Code', '', 27.00, 'RECORD', 'Metal'),
('Veeze / Ganger', '', 70.00, 'RECORD', 'Hip-Hop'),
('4, 5, 6 / Kool G Rap', '', 33.00, 'RECORD', 'Hip-Hop'),
('Saga of the Swamp Thing, Book 1 by Alan Moore', '', 18.00, 'COMIC', 'DC'),
('Assassin Nation Full Series 1-5 by Kyle Starks', '', 15.00, 'COMIC', 'Image');

INSERT INTO product_tags (product_id, tag_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 4),
(6, 5),
(7, 5),
(8, 6),
(9, 6),
(10, 7),
(11, 7),
(12, 8),
(13, 9);

INSERT INTO orders (order_date, packaging_cost, status) VALUES
(NOW(), 2.50, 'PROCESSING'),
(NOW(), 3.00, 'SHIPPED');

INSERT INTO order_items (order_id, product_id, quantity) VALUES
(1, 1, 1),
(1, 4, 2),
(2, 12, 1),
(2, 10, 1);