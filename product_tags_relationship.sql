USE dylans_yard_sale; 

SELECT product_id, COUNT(*) AS tag_count
FROM product_tags
GROUP BY product_id;


SELECT tag_id, COUNT(*) AS product_count
FROM product_tags
GROUP BY tag_id;
