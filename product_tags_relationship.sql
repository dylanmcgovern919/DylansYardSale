USE dylans_yard_sale; --notes: Make sure queries run against the correct database.

SELECT product_id, COUNT(*) AS tag_count
FROM product_tags
GROUP BY product_id;
--notes: Counts how many tags each product has.
--notes: If any product_id has tag_count > 1, that proves one product can have many tags (first "many" side).

SELECT tag_id, COUNT(*) AS product_count
FROM product_tags
GROUP BY tag_id;
--notes: Counts how many products each tag has.
--notes: If any tag_id has product_count > 1, that proves one tag can belong to many products (second "many" side).

--notes: Together, these two queries prove a many-to-many relationship between products and tags via product_tags.
--notes: Suggested file name: many_to_many_proof.sql