# Dylan's Yard Sale API

A Spring Boot + JPA REST API for a resale inventory and order workflow built around the items I resell most: **records, comics, and clothing**.

This project models a small resale operation where you can:
- manage inventory products,
- classify products with reusable tags,
- create and manage orders,
- and manage nested order items tied to specific products.

The API is backed by MySQL and documented/tested with Swagger UI.

---

## Project Scope

### Business Domain
I resell:
- **Records**
- **Comics**
- **Clothing**

Those categories are represented directly in the API using the `ProductCategory` enum:
- `RECORD`
- `COMIC`
- `CLOTHING`

### Core Entities
- `Product`
- `Tag`
- `Order`
- `OrderItem`

---

## Final Project Requirement Coverage

### 1) At least 3 entities / 3 tables
Met.
- Entities: Product, Tag, Order, OrderItem
- Tables: `products`, `tags`, `orders`, `order_items`, plus join table `product_tags`

### 2) CRUD operations required
Met.
- **Product:** full CRUD
- **Tag:** full CRUD
- **Order:** full CRUD
- **OrderItem:** full CRUD (nested under orders)

### 3) Each entity has at least one CRUD operation
Met.
- All entities have multiple operations.

### 4) At least one entity with all 4 CRUD operations
Met.
- Product, Tag, and Order all provide full CRUD.

### 5) At least one one-to-many relationship
Met.
- `Order (1) -> (many) OrderItem`

### 6) At least one many-to-many relationship with CRUD operations on the relationship
Met.
- `Product (many) <-> (many) Tag` via `product_tags`
- Relationship operations implemented:
  - add tag to product
  - list tags on product
  - remove tag from product

### 7) REST API tested via Swagger/Postman/ARC/front end
Met.
- Swagger UI is configured and used for endpoint testing and validation evidence.

---

## Additional Features Beyond Minimum Requirements

- DTO response mapping to avoid exposing JPA entities directly.
- Global exception handling (`GlobalExceptionHandler`) for consistent JSON error responses.
- Validation with Jakarta Bean Validation annotations.
- Order status filtering endpoint:
  - `GET /api/orders/status/{status}`
- Dev seed profile (`dev-seed`) to load sample resale inventory data.
- Unit/web-layer tests for controllers and order-item service behavior.
- SQL scripts included for schema creation and relationship inspection.

---

## Database Design

### Main Tables
- `products`
- `tags`
- `orders`
- `order_items`
- `product_tags` (join table)

### Relationships
- **One-to-many:** `orders.id` -> `order_items.order_id`
- **Many-to-many:** `products.id` <-> `tags.id` through `product_tags`

### SQL / ERD Artifacts in Repo
- `/home/runner/work/DylansYardSale/DylansYardSale/dylans_yard_sale.sql`
- `/home/runner/work/DylansYardSale/DylansYardSale/check_database.sql`
- `/home/runner/work/DylansYardSale/DylansYardSale/product_tags_relationship.sql`
- `/home/runner/work/DylansYardSale/DylansYardSale/eer_diagram.pdf`
- `/home/runner/work/DylansYardSale/DylansYardSale/One_to_many.pdf`
- `/home/runner/work/DylansYardSale/DylansYardSale/many_to_many.pdf`

---

## API Endpoint Inventory

### Product Endpoints
- `GET /api/products`
- `GET /api/products/{id}`
- `POST /api/products`
- `PUT /api/products/{id}`
- `DELETE /api/products/{id}`

### Product-Tag Relationship Endpoints
- `POST /api/products/{id}/tags?tagName=...`
- `GET /api/products/{id}/tags`
- `DELETE /api/products/{id}/tags/{tagId}`

### Tag Endpoints
- `GET /api/tags`
- `GET /api/tags/{id}`
- `POST /api/tags`
- `PUT /api/tags/{id}`
- `DELETE /api/tags/{id}`

### Order Endpoints
- `GET /api/orders`
- `GET /api/orders/{id}`
- `GET /api/orders/status/{status}`
- `POST /api/orders`
- `PUT /api/orders/{id}`
- `DELETE /api/orders/{id}`

### Nested OrderItem Endpoints
- `GET /api/orders/{orderId}/items`
- `GET /api/orders/{orderId}/items/{itemId}`
- `POST /api/orders/{orderId}/items`
- `PUT /api/orders/{orderId}/items/{itemId}`
- `DELETE /api/orders/{orderId}/items/{itemId}`

---

## Tech Stack

- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JPA
- MySQL
- Springdoc OpenAPI / Swagger UI
- Maven
- JUnit + MockMvc + Mockito

---

## Run Locally

## 1) Clone and enter project
```bash
git clone https://github.com/dylanmcgovern919/DylansYardSale.git
cd DylansYardSale
```

## 2) Create database
Run:
```sql
source dylans_yard_sale.sql;
```

## 3) Configure datasource
Update MySQL credentials in:
- `/home/runner/work/DylansYardSale/DylansYardSale/src/main/resources/application.yml`

## 4) Start API
```bash
./mvnw spring-boot:run
```

(Windows)
```bash
mvnw.cmd spring-boot:run
```

---

## Swagger Usage Guide

### Open Swagger UI
Once the app is running:
- `http://localhost:8080/swagger-ui/index.html`

### Recommended testing flow
1. Start with `GET /api/products` to verify connectivity and seeded data.
2. Run `POST /api/products` with a valid product body.
3. Run `GET /api/products/{id}` using the new ID.
4. Run `PUT /api/products/{id}` to verify update behavior.
5. Run `POST /api/products/{id}/tags` to attach tags.
6. Run `GET /api/products/{id}/tags` to verify many-to-many data.
7. Run order endpoints (`POST /api/orders`, nested item operations) to validate one-to-many flow.
8. Try invalid payloads/enums to verify 400 handling from global exception responses.

### Example product payload
```json
{
  "name": "Minor Threat - Out of Step",
  "description": "Original pressing",
  "price": 34.99,
  "category": "RECORD",
  "genre": "Punk"
}
```

---

## Swagger Evidence (Screenshots)

### Swagger home
![Swagger Home](correct_swagger_home.png)

### GET products
![GET Products](get_products.png)

### POST product success examples
![POST Product Success 1](correct_post_product_1.png)
![POST Product Success 2](correct_post_product_2.png)
![POST Product Success 3](correct_post_product_3.png)

### Validation/error example
![Invalid POST Example](invalid_post.png)

---

## Testing

Run tests:
```bash
./mvnw test
```

Note: local integration/context startup tests require a reachable MySQL configuration.

---

## Packaging / Build

```bash
./mvnw clean package
```

---

## Project Structure

- `controller` – REST endpoints
- `service` / `service.impl` – business logic
- `repository` – JPA repositories
- `model` – JPA entities and enums
- `dto` – request/response DTOs
- `exception` – custom and global API error handling
- `config` – OpenAPI config and dev seed loader

---

## Instructor Notes

This API is structured to align with beginner Spring Boot backend expectations:
- clear layered architecture,
- explicit entity relationships,
- full CRUD coverage,
- validation and exception handling,
- and demonstrable testing/documentation via Swagger and tests.
