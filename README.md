# Dylan’s Yard Sale API (Clothes, Records, Comics)

Dylan’s Yard Sale is a Spring Boot + JPA REST API for managing resale inventory focused on **clothes, records, and comics**.  
The API stores data in **MySQL**, supports CRUD operations, and includes Swagger UI for endpoint testing.

This project was built to satisfy the final bootcamp Spring rubric requirements while documenting relationship design and testing evidence directly in the repository.

---

## Tech Stack

- Java 21
- Spring Boot 3.3.4
- Spring Data JPA (Hibernate)
- MySQL
- Jakarta Validation
- springdoc OpenAPI / Swagger UI

---

## What This API Does

- Tracks resale inventory items (products) for clothes, records, and comics.
- Persists data with Spring Data JPA + MySQL.
- Exposes REST endpoints for create/read/update/delete operations.
- Implements one-to-many and many-to-many relationships.
- Provides Swagger UI for interactive testing and grading evidence.

---

## FINAL PROJECT REQUIREMENTS (Course Rubric)

This project follows the Spring Boot Web API final-project requirements:

- Database design contains at least 3 entities and 3 tables
- Contains CRUD operations
- Each entity has at least one CRUD operation
- One or more entities have full CRUD (Create, Read, Update, Delete)
- Contains at least one one-to-many relationship
- Contains at least one many-to-many relationship with one or more CRUD operations on that relationship
- REST Web API server is tested through Swagger/Postman/ARC or a front-end client

Submission artifacts included in this repo:

- Source code for all layers (controller, service, repository, entity/model, exception handling)
- SQL files
- ERD/relationship PDFs
- Additional documentation (this README)

---

## Rubric Alignment (Final Project Requirements)

### 1) At least 3 entities and 3 tables ✅
Implemented entities:
1. `Product`
2. `Tag`
3. `Order`
4. `OrderItem`

Database tables include:
- `products`
- `tags`
- `orders`
- `order_items`
- `product_tags` (join table)

Evidence in repository:
- [`dylans_yard_sale.sql`](./dylans_yard_sale.sql)
- [`product_tags_relationship.sql`](./product_tags_relationship.sql)
- [`check_database.sql`](./check_database.sql)
- [`eer_diagram.pdf`](./eer_diagram.pdf)

### 2) CRUD operations included ✅
Each entity has CRUD coverage, and at least one entity has full CRUD.

### 3) One-to-many relationship ✅
- `Order -> OrderItem` (`@OneToMany` / `@ManyToOne`)
- Evidence:
  - [`One_to_many.pdf`](./One_to_many.pdf)
  - [`eer_diagram.pdf`](./eer_diagram.pdf)

### 4) Many-to-many relationship with CRUD on relationship ✅
- `Product <-> Tag` via `product_tags`
- Evidence:
  - [`many_to_many.pdf`](./many_to_many.pdf)
  - [`product_tags_relationship.sql`](./product_tags_relationship.sql)

### 5) REST API tested with Swagger/Postman/ARC ✅
Swagger UI and endpoint screenshots in repository root:
- [`correct_swagger_home.png`](./correct_swagger_home.png)
- [`correct_post_product_1.png`](./correct_post_product_1.png)
- [`correct_post_product_2.png`](./correct_post_product_2.png)
- [`correct_post_product_3.png`](./correct_post_product_3.png)
- [`get_products.png`](./get_products.png)
- [`invalid_post.png`](./invalid_post.png)

---

## API Endpoint Coverage

### Product (`/api/products`)
- `POST /api/products`
- `GET /api/products`
- `GET /api/products/{id}`
- `PUT /api/products/{id}`
- `DELETE /api/products/{id}`

### Tag (`/api/tags`)
- `POST /api/tags`
- `GET /api/tags`
- `GET /api/tags/{id}`
- `PUT /api/tags/{id}`
- `DELETE /api/tags/{id}`

### Order (`/api/orders`)
- `POST /api/orders`
- `GET /api/orders`
- `GET /api/orders/{id}`
- `GET /api/orders/status/{status}`
- `PUT /api/orders/{id}`
- `DELETE /api/orders/{id}`

### OrderItem (nested resource)
- `POST /api/orders/{orderId}/items`
- `GET /api/orders/{orderId}/items`
- `GET /api/orders/{orderId}/items/{itemId}`
- `PUT /api/orders/{orderId}/items/{itemId}`
- `DELETE /api/orders/{orderId}/items/{itemId}`

### Product-Tag relationship
- `POST /api/products/{id}/tags?tagName=...`
- `GET /api/products/{id}/tags`
- `DELETE /api/products/{id}/tags/{tagId}`

---

## Validation and Error Handling

### Validation
- Product: required name, price, category; non-negative price
- Tag: non-blank unique name
- Order: required status, non-negative packagingCost, non-empty items
- Order item request: required productId, positive quantity

### Global Exception Handling
- `404`: Resource not found
- `400`: validation errors
- `400`: malformed JSON / invalid enum
- `400`: illegal argument / integrity violations

---

## Swagger / OpenAPI

- Swagger UI URL: `http://localhost:8080/swagger-ui/index.html`

### Swagger Testing Evidence
- [`correct_swagger_home.png`](./correct_swagger_home.png)
- [`correct_post_product_1.png`](./correct_post_product_1.png)
- [`correct_post_product_2.png`](./correct_post_product_2.png)
- [`correct_post_product_3.png`](./correct_post_product_3.png)
- [`get_products.png`](./get_products.png)
- [`invalid_post.png`](./invalid_post.png)

---

## Database / Submission Artifacts

Included in repository root:
- [`dylans_yard_sale.sql`](./dylans_yard_sale.sql) (schema + seed data)
- [`check_database.sql`](./check_database.sql) (verification queries)
- [`product_tags_relationship.sql`](./product_tags_relationship.sql) (relationship checks)
- [`eer_diagram.pdf`](./eer_diagram.pdf)
- [`One_to_many.pdf`](./One_to_many.pdf)
- [`many_to_many.pdf`](./many_to_many.pdf)

---

## Author

Dylan McGovern  
GitHub: [`dylanmcgovern919`](https://github.com/dylanmcgovern919)
