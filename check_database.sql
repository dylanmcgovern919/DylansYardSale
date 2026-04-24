USE dylans_yard_sale; --notes: Ensures all checks run against the correct database.

SHOW TABLES; --notes: Quick proof that expected tables exist.

SELECT COUNT(*) AS products_count FROM products;
SELECT COUNT(*) AS tags_count FROM tags;
SELECT COUNT(*) AS product_tags_count FROM product_tags;
SELECT COUNT(*) AS orders_count FROM orders;
SELECT COUNT(*) AS order_items_count FROM order_items;
--notes: Row-count proof for seed data completeness.

SELECT p.id, p.name, t.name AS tag_name
FROM product_tags pt
JOIN products p ON p.id = pt.product_id
JOIN tags t ON t.id = pt.tag_id
ORDER BY p.id, t.name;
--notes: Verifies many-to-many links between products and tags.

SELECT o.id AS order_id, o.status, oi.id AS order_item_id, oi.product_id, oi.quantity
FROM orders o
JOIN order_items oi ON oi.order_id = o.id
ORDER BY o.id, oi.id;
--notes: Verifies one-to-many links from orders to order_items.