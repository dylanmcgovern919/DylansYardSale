# Dylan's Yard Sale API

## Intro (why I made this + what it does)

I built this API because I resell **records, comics, and clothing** and needed one backend to manage my inventory, product tagging, and order flow in a consistent way.

This is a Spring Boot + JPA + MySQL REST API that supports full CRUD across the core entities, includes one-to-many and many-to-many relationship management, validates incoming request data, and returns structured API errors.

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

## Additional API features (NOT required by rubric)

- **DTO-based responses** (`ProductResponse`, `TagResponse`, `OrderResponse`, `OrderItemResponse`) so entities are not exposed directly.
- **Global exception handler** with consistent JSON errors for validation, bad JSON, invalid parameters, not found, and DB constraint issues.
- **Order status filter endpoint**:
  - `GET /api/orders/status/{status}`
- **Nested order item endpoints** under `/api/orders/{orderId}/items/*` for cleaner parent-child API design.
- **Request validation rules** (non-empty order items, quantity > 0, required fields, non-negative packaging cost).
- **Case-insensitive tag attach behavior** when adding tags to products.
- **Optional `dev-seed` startup profile** (`DataLoader`) for local sample data bootstrapping.
- **Custom OpenAPI metadata configuration** in `OpenApiConfig`.

---

## Repository audit result (entire repo reviewed)

- ✅ I did a full repository pass across controllers, services, entities, repositories, DTOs, config, SQL scripts, tests, and README.
- ✅ No missing rubric requirements were found in the current implementation.
- ⚠️ Operational note: full `./mvnw test` includes `DylansYardSaleApplicationTests`, which requires a reachable MySQL datasource.

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

Use the default `application.yml` datasource values:
- URL: `jdbc:mysql://localhost:3306/dylans_yard_sale?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true`
- Username: `root`
- Password: `349215`
- Server port: `8080`

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

- Tightened one-to-many lifecycle handling so replacing order items cleanly removes orphaned child rows.
- Improved tag attachment flow to use case-insensitive lookup before creating tags.

---

## Tests

Run tests:
- `./mvnw test`

Important:
- `DylansYardSaleApplicationTests` is a full Spring context test and expects a reachable MySQL datasource.
- If MySQL is not running/configured, that context test will fail even when controller/service tests pass.
