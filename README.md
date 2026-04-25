# Dylan's Yard Sale API

Spring Boot REST API for products, tags, orders, and order items for a vintage/resale shop.

## Tech Stack
- Java 21
- Spring Boot 3.3.4
- Spring Data JPA (Hibernate)
- MySQL
- Jakarta Validation
- springdoc OpenAPI / Swagger UI

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

### 2) CRUD coverage ✅
Each entity has CRUD coverage, and multiple entities have full CRUD.

#### Product (`/api/products`)
- `POST /api/products`
- `GET /api/products`
- `GET /api/products/{id}`
- `PUT /api/products/{id}`
- `DELETE /api/products/{id}`

#### Tag (`/api/tags`)
- `POST /api/tags`
- `GET /api/tags`
- `GET /api/tags/{id}`
- `PUT /api/tags/{id}`
- `DELETE /api/tags/{id}`

#### Order (`/api/orders`)
- `POST /api/orders`
- `GET /api/orders`
- `GET /api/orders/{id}`
- `GET /api/orders/status/{status}`
- `PUT /api/orders/{id}`
- `DELETE /api/orders/{id}`

#### OrderItem (nested resource)
- `POST /api/orders/{orderId}/items`
- `GET /api/orders/{orderId}/items`
- `GET /api/orders/{orderId}/items/{itemId}`
- `PUT /api/orders/{orderId}/items/{itemId}`
- `DELETE /api/orders/{orderId}/items/{itemId}`

### 3) One-to-many relationship ✅
- `Order` -> `OrderItem` (`@OneToMany` / `@ManyToOne`)

### 4) Many-to-many relationship with CRUD on relationship ✅
- `Product` <-> `Tag` via `product_tags`
- Relationship endpoints:
  - `POST /api/products/{id}/tags?tagName=...`
  - `GET /api/products/{id}/tags`
  - `DELETE /api/products/{id}/tags/{tagId}`

### 5) REST API testing evidence (Swagger) ✅
Swagger/OpenAPI used to test endpoints during development.

---

## Validation and Error Handling

### Validation
- Product: required `name`, `price`, `category`; non-negative `price`
- Tag: non-blank unique `name`
- Order: required `status`, required non-negative `packagingCost`, non-empty `items`
- Order item request: required `productId`, positive `quantity`

### Global Exception Handling
- 404: `ResourceNotFoundException`
- 400: validation errors (`MethodArgumentNotValidException`)
- 400: malformed JSON / invalid enum
- 400: illegal argument / integrity violations

---

## Swagger / OpenAPI

### URL
- `http://localhost:8080/swagger-ui/index.html`

### Swagger Screenshots
- ![invalid-post-1](https://github.com/user-attachments/assets/38743bbd-4321-4756-8db8-3286775e53da)
- ![swagger-2](https://github.com/user-attachments/assets/20c0834b-8ae1-47c6-a712-0c6a1e217c58)
- ![swagger-3](https://github.com/user-attachments/assets/300a5b57-4be8-458e-be1c-b8ead0287aef)
- ![swagger-4](https://github.com/user-attachments/assets/e1cf77d2-5747-4fb3-a1da-df2997a57f08)
- ![get-products](https://github.com/user-attachments/assets/adb39e52-0a3f-4bf7-bdd6-8a745d722ddd)
- ![swagger-home-correct](https://github.com/user-attachments/assets/7b2b9c3d-69e8-4b21-b26f-63cbcd8269ca)
- ![invalid-post](https://github.com/user-attachments/assets/0057e580-df42-4fbc-934c-725a070b7788)

---

## Database / Submission Artifacts
Included in repository root:
- `dylans_yard_sale.sql` (schema + seed data)
- `check_database.sql` (verification queries)
- `product_tags_relationship.sql` (relationship checks)
- `eer_diagram.pdf`
- `One_to_many.pdf`
- `many_to_many.pdf`

---

## Example Requests

### Create Product
`POST /api/products`
```json
{
  "name": "Bad Brains - Rock for Light",
  "description": "Original pressing hardcore LP",
  "price": 38.0,
  "category": "RECORD",
  "genre": "Punk"
}
```

### Create Tag
`POST /api/tags`
```json
{
  "name": "Hardcore"
}
```

### Create Order
`POST /api/orders`
```json
{
  "status": "PROCESSING",
  "packagingCost": 2.5,
  "items": [
    { "productId": 1, "quantity": 1 },
    { "productId": 12, "quantity": 1 }
  ]
}
```

---

## Author
**Dylan McGovern**

GitHub: [dylanmcgovern919](https://github.com/dylanmcgovern919)
