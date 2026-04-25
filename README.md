# Dylan's Yard Sale API

A Spring Boot REST API for managing products, tags, and customer orders for a vintage/resale style shop.

Built with:
- Java 21
- Spring Boot 3
- Spring Data JPA (Hibernate)
- MySQL
- Spring Validation
- Swagger / OpenAPI (springdoc)

---

## Project Summary

This project implements a REST Web API server using JPA with:
- Multiple entities and database tables
- Full CRUD operations
- One-to-many and many-to-many relationships
- Validation and global exception handling
- Swagger documentation for endpoint testing

---

## Database / Entity Design

### Entities
1. **Product**
2. **Tag**
3. **Order**
4. **OrderItem**

### Relationships
- **One-to-Many**: `Order` → `OrderItem`
- **Many-to-Many**: `Product` ↔ `Tag` (via `product_tags` join table)

---

## CRUD Coverage

### Product (`/api/products`)
- **Create**: `POST /api/products`
- **Read**: `GET /api/products`, `GET /api/products/{id}`
- **Update**: `PUT /api/products/{id}`
- **Delete**: `DELETE /api/products/{id}`

### Tag (`/api/tags`)
- **Create**: `POST /api/tags`
- **Read**: `GET /api/tags`, `GET /api/tags/{id}`
- **Update**: `PUT /api/tags/{id}`
- **Delete**: `DELETE /api/tags/{id}`

### Order (`/api/orders`)
- **Create**: `POST /api/orders`
- **Read**: `GET /api/orders`, `GET /api/orders/{id}`, `GET /api/orders/status/{status}`
- **Update**: `PUT /api/orders/{id}`
- **Delete**: `DELETE /api/orders/{id}`

### Relationship CRUD (Product-Tags)
- **Add Tag to Product**: `POST /api/products/{id}/tags?tagName=...`
- **Get Product Tags**: `GET /api/products/{id}/tags`
- **Remove Tag from Product**: `DELETE /api/products/{id}/tags/{tagId}`

### OrderItem Design (Nested Resource via Orders)

`OrderItem` is implemented as a child entity of `Order` and is intentionally managed through `/api/orders` endpoints rather than standalone `/api/order-items` endpoints.

This still provides CRUD coverage for `OrderItem` within the Order workflow:

- **Create OrderItem(s)**: `POST /api/orders` using `items` in the request body  
- **Read OrderItem(s)**: `GET /api/orders` and `GET /api/orders/{id}` (items returned inside each order)  
- **Update OrderItem(s)**: `PUT /api/orders/{id}` with updated `items`  
- **Delete OrderItem(s)**: `DELETE /api/orders/{id}` (cascades to `order_items`)

---

## Rubric Requirements Checklist

### ✅ 1-person project requirements
- [x] Database design contains at least 3 entities and 3 tables  
- [x] Contains CRUD operations  
- [x] Each entity has at least one CRUD operation  
- [x] At least one entity has all 4 CRUD operations (Product, Tag, and Order each do)  
- [x] Contains at least one one-to-many relationship (`Order` → `OrderItem`)  
- [x] Contains at least one many-to-many relationship with CRUD operations (`Product` ↔ `Tag`)  
- [x] REST Web API server tested via Swagger/Postman/ARC  

---

## Extra Features

- **Global exception handling** with standardized error responses:
  - `ResourceNotFoundException` → 404
  - Validation failures → 400
  - Illegal arguments → 400

- **Input validation** using Jakarta Validation:
  - Non-empty order item list
  - Required product IDs
  - Positive item quantity
  - Non-negative packaging cost
  - Required order status
  - Non-blank tag names

- **Order creation with line-item quantities**
  - Uses `OrderItemRequest` DTO for product + quantity in each order item

- **Status-based order filtering**
  - `GET /api/orders/status/{status}`

- **Tag duplicate guard**
  - Prevents duplicate tag creation by name in controller logic

- **Seed data profile**
  - `DataLoader` runs only in `dev-seed` profile to avoid duplicate seeding in normal runs

- **OpenAPI / Swagger UI integration** for interactive API testing

---

## Swagger Evidence

Swagger/OpenAPI was used to test API endpoints during development.

### Link
- **Swagger UI:** `http://localhost:8080/swagger-ui.html`

### Screenshots
- ![Swagger UI Home](docs/screenshots/swagger-home.png)
- ![Successful POST Product](docs/screenshots/swagger-post-product-success.png)
- ![GET Products Response](docs/screenshots/swagger-get-products.png)
- ![Validation Error 400](docs/screenshots/swagger-validation-400.png)

### Verified in Swagger
- Product CRUD (`/api/products`)
- Tag CRUD (`/api/tags`)
- Order CRUD (`/api/orders`)
- Product-Tag relationship endpoints
- `GET /api/orders/status/{status}`

---

## How to Access the API (Swagger)

After starting the Spring Boot application, open:

- **Swagger UI:** `http://localhost:8080/swagger-ui.html`

Use Swagger UI to view and test all endpoints directly in the browser.

---

## Example JSON Requests

### Create Product
`POST /api/products`
```json
{
  "name": "Vintage Denim Jacket",
  "description": "1980s washed denim jacket",
  "price": 79.99,
  "category": "CLOTHING",
  "genre": null
}
```

### Create Tag
`POST /api/tags`
```json
{
  "name": "Vintage"
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
    { "productId": 2, "quantity": 1 }
  ]
}
```

---

## Author

**Dylan McGovern**  
GitHub: [dylanmcgovern919](https://github.com/dylanmcgovern919)
