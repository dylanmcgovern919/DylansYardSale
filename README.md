# Dylan's Yard Sale API

I resell **records, comics, and clothing**, and this project is my backend API for managing inventory, tags, and customer orders.

It is a Spring Boot + JPA + MySQL REST API with full CRUD, relationship management, validation, and global error handling.

---

## What this repository contains (entire project map)

- **Spring Boot app source**
  - `/src/main/java/com/dylansyardsale`
    - `controller/` — REST endpoints
    - `service/` and `service/impl/` — business logic
    - `repository/` — JPA repositories
    - `model/` — JPA entities and enums
    - `dto/` — request/response DTOs
    - `exception/` — custom exception + global API error handling
    - `config/` — OpenAPI metadata and optional seed data loader
- **Configuration**
  - `/src/main/resources/application.yml` — default runtime config
  - `/src/main/resources/application-dev.yml` — dev profile override
- **Tests**
  - `/src/test/java/com/dylansyardsale`
    - web/controller tests
    - service-layer test
    - app context test
- **Database and design artifacts**
  - `dylans_yard_sale.sql`
  - `product_tags_relationship.sql`
  - `check_database.sql`
  - `eer_diagram.pdf`
  - `One_to_many.pdf`
  - `many_to_many.pdf`
- **Build tooling**
  - `pom.xml`
  - `mvnw`, `mvnw.cmd`, `.mvn/wrapper/*`

---

## Tech stack

- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JPA
- MySQL
- Jakarta Validation
- springdoc-openapi (Swagger UI)
- JUnit + Mockito + MockMvc

---

## Rubric requirement coverage (where/how each requirement is met)

### 1) Database design with at least 3 entities / 3 tables ✅
- `Product` → `products` table
- `Tag` → `tags` table
- `Order` → `orders` table
- `OrderItem` → `order_items` table
- Join table for many-to-many:
  - `product_tags`

### 2) Contains CRUD operations ✅
- **Product**: create/read/update/delete in `ProductController`
- **Tag**: create/read/update/delete in `TagController`
- **Order**: create/read/update/delete in `OrderController`
- **OrderItem**: nested create/read/update/delete in `OrderItemController`

### 3) Each entity has at least one CRUD operation ✅
- All entities have multiple operations; several have full CRUD.

### 4) One or more entities has all 4 CRUD operations ✅
- `Product`, `Tag`, `Order`, and `OrderItem` all have full CRUD paths.

### 5) At least one one-to-many relationship ✅
- `Order (1) -> (many) OrderItem`
- Implemented in `Order.items` and `OrderItem.order`
- Nested routes under `/api/orders/{orderId}/items`

### 6) At least one many-to-many relationship + CRUD on relationship ✅
- `Product <-> Tag` many-to-many via `product_tags`
- Relationship operations:
  - `POST /api/products/{id}/tags?tagName=...` (attach/create+attach)
  - `GET /api/products/{id}/tags` (read relationship)
  - `DELETE /api/products/{id}/tags/{tagId}` (detach)

### 7) REST API tested through Swagger/Postman/ARC/front-end ✅
- Swagger/OpenAPI enabled:
  - `/swagger-ui/index.html`
  - `/v3/api-docs`
- Includes test suite with controller/service tests under `src/test/java`.

---

## API additions beyond minimum rubric requirements

- **DTO-based API responses** (`ProductResponse`, `TagResponse`, `OrderResponse`, `OrderItemResponse`) to avoid exposing JPA entities directly.
- **Global exception handling** (`GlobalExceptionHandler`) with structured JSON errors for validation, not found, malformed payload, and constraint issues.
- **Order status filtering endpoint**:
  - `GET /api/orders/status/{status}`
- **Validation-first request models**:
  - Quantity > 0, non-empty order items, required fields
- **Optional seed data profile** (`dev-seed`) via `DataLoader`.
- **OpenAPI metadata customization** in `OpenApiConfig`.

---

## API endpoint summary

### Products
- `GET /api/products`
- `GET /api/products/{id}`
- `POST /api/products`
- `PUT /api/products/{id}`
- `DELETE /api/products/{id}`
- `POST /api/products/{id}/tags?tagName=Vintage`
- `GET /api/products/{id}/tags`
- `DELETE /api/products/{id}/tags/{tagId}`

### Tags
- `GET /api/tags`
- `GET /api/tags/{id}`
- `POST /api/tags`
- `PUT /api/tags/{id}`
- `DELETE /api/tags/{id}`

### Orders
- `GET /api/orders`
- `GET /api/orders/{id}`
- `GET /api/orders/status/{PROCESSING|SHIPPED|COMPLETED}`
- `POST /api/orders`
- `PUT /api/orders/{id}`
- `DELETE /api/orders/{id}`

### Order Items (nested under order)
- `GET /api/orders/{orderId}/items`
- `GET /api/orders/{orderId}/items/{itemId}`
- `POST /api/orders/{orderId}/items`
- `PUT /api/orders/{orderId}/items/{itemId}`
- `DELETE /api/orders/{orderId}/items/{itemId}`

---

## Setup and run

### 1) Prerequisites
- Java 21
- Maven (or use `./mvnw`)
- MySQL 8+

### 2) Configure database
Create a database named:
- `dylans_yard_sale`

Set environment variables (recommended):
- `DB_URL` (optional; defaults to local `dylans_yard_sale`)
- `DB_USERNAME` (optional; default `root`)
- `DB_PASSWORD` (optional; default empty)
- `SERVER_PORT` (optional; default `8080`)

### 3) Start the API
From repo root:
- `./mvnw spring-boot:run`

Optional seed data:
- run with profile `dev-seed` enabled so starter products/tags load once.

### 4) Open Swagger
- `http://localhost:8080/swagger-ui/index.html`

---

## Example request payloads

### Create Product
`POST /api/products`
```json
{
  "name": "Vintage Burberry Hat",
  "description": "Classic check pattern",
  "price": 45.0,
  "category": "CLOTHING",
  "genre": null
}
```

### Create Order
`POST /api/orders`
```json
{
  "status": "PROCESSING",
  "packagingCost": 2.99,
  "items": [
    { "productId": 1, "quantity": 2 },
    { "productId": 3, "quantity": 1 }
  ]
}
```

---

## Notes from project review/refactor pass

- Removed hardcoded database password from config and moved to environment-variable based configuration.
- Tightened one-to-many lifecycle handling so replacing order items cleanly removes orphaned child rows.
- Improved tag attachment flow to use case-insensitive lookup before creating tags.

---

## Tests

Run tests:
- `./mvnw test`

Important:
- `DylansYardSaleApplicationTests` is a full Spring context test and expects a reachable MySQL datasource.
- If MySQL is not running/configured, that context test will fail even when controller/service tests pass.
