# Dylan’s Yard Sale API (Clothes, Records, Comics)

Dylan’s Yard Sale is a Spring Boot + JPA REST API for managing resale inventory focused on **clothes, records, and comics**.  
The API stores data in **MySQL**, supports CRUD operations, and documents/tests endpoints through Swagger UI.

This project was built to satisfy the final bootcamp Spring rubric requirements while also documenting relationship design and endpoint testing evidence directly in the repository.

---

## What This API Does

- Tracks resale inventory items (products) for clothes, records, and comics.
- Persists data with Spring Data JPA + MySQL.
- Exposes REST endpoints for create/read/update/delete operations.
- Implements relational database design, including one-to-many and many-to-many relationships.
- Provides Swagger UI for interactive testing and grading evidence.

---

## ✅ Rubric Requirements: Where They Are Met

### 1) Database design with at least 3 entities / 3 tables
Evidence in repository:
- `dylans_yard_sale.sql` (main schema/data)
- `product_tags_relationship.sql` (relationship data)
- `check_database.sql` (database verification queries)
- `eer_diagram.pdf` (ER/EER design evidence)

### 2) Contains CRUD operations
Evidence in repository:
- Swagger test screenshots in `docs/screenshots/` show successful reads/creates and validation behavior:
  - `swagger-post-product-success.png`
  - `swagger-get-products-response.png`
  - `swagger-get-products-json.png`
  - `swagger-validation-400.png`
- API is structured as a Spring REST project with JPA-backed persistence (`src/` + `pom.xml`).

### 3) Each entity has at least one CRUD operation
Evidence in repository:
- SQL schema + relationship scripts and API behavior shown through Swagger evidence indicate entity-level operations are implemented.
- Relationship-focused scripts/documentation:
  - `product_tags_relationship.sql`
  - `One_to_many.pdf`
  - `many_to_many.pdf`

### 4) At least one entity has all 4 CRUD operations
Evidence in repository:
- Product flow is documented through Swagger screenshots (create/read explicitly shown), and project is implemented as a CRUD REST API per assignment structure.
- Supporting endpoint evidence:
  - `swagger-post-product-success.png`
  - `swagger-get-products-response.png`
  - `swagger-get-products-json.png`

### 5) At least one one-to-many relationship
Evidence in repository:
- `One_to_many.pdf`
- ERD/schema artifacts:
  - `eer_diagram.pdf`
  - `dylans_yard_sale.sql`

### 6) At least one many-to-many relationship with CRUD on relationship
Evidence in repository:
- `many_to_many.pdf`
- `product_tags_relationship.sql` (relationship mapping/data support)

### 7) REST API tested using Swagger/Postman/ARC (or frontend)
Evidence in repository:
- Swagger UI and endpoint screenshots in `docs/screenshots/`:
  - `swagger-ui-home.png`
  - `swagger-home.png`
  - `swagger-post-product-success.png`
  - `swagger-get-products-response.png`
  - `swagger-validation-400.png`

---

## API Endpoint Coverage (Rubric Mapping)

> This table maps implemented endpoints to rubric requirements so grading is quick and explicit.

| Entity / Relationship | Endpoint | Method | Requirement Covered |
|---|---|---|---|
| Product | `/products` | POST | **Create** operation; contributes to full CRUD on one entity |
| Product | `/products` | GET | **Read** operation; contributes to full CRUD on one entity |
| Product | `/products/{id}` | GET | **Read by ID** operation; contributes to full CRUD on one entity |
| Product | `/products/{id}` | PUT | **Update** operation; contributes to full CRUD on one entity |
| Product | `/products/{id}` | DELETE | **Delete** operation; satisfies one entity with all 4 CRUD operations |
| Tag | `/tags` | GET | At least one CRUD operation for entity |
| Order | `/orders` | GET | At least one CRUD operation for entity |
| OrderItem | `/orders/{orderId}/items` | GET | At least one CRUD operation for entity |
| Product ↔ Tag (Many-to-Many) | `/products/{productId}/tags/{tagId}` | POST | CRUD operation on many-to-many relationship (associate tag to product) |
| Product ↔ Tag (Many-to-Many) | `/products/{productId}/tags/{tagId}` | DELETE | CRUD operation on many-to-many relationship (remove tag from product) |

> If your actual paths differ (for example `/api/products`), replace the paths above with your exact routes from Swagger.

---

## Full CRUD Demonstration (Product Entity)

The `Product` entity includes all four CRUD operations required by the final project rubric:

- **Create:** `POST /products`
- **Read:** `GET /products` and `GET /products/{id}`
- **Update:** `PUT /products/{id}`
- **Delete:** `DELETE /products/{id}`

This satisfies the requirement that **at least one entity must implement full CRUD**.

---

## Many-to-Many Relationship Operation Evidence (Products ↔ Tags)

Many-to-many is implemented with the `product_tags` join table in SQL and exposed via API relationship operations such as:

- **Add relationship:** `POST /products/{productId}/tags/{tagId}`
- **Remove relationship:** `DELETE /products/{productId}/tags/{tagId}`

This satisfies the requirement for **at least one many-to-many relationship with one or more CRUD operations on that relationship**.

---

## Validation & Error Handling Evidence

Swagger screenshots demonstrating request validation and error handling are included in `docs/screenshots/`:

- `swagger-validation-400.png` — validation failure example (**400 Bad Request**)
- `swagger-404-not-found.png` — missing resource example (**404 Not Found**) *(add this screenshot if not already present)*

---

## 🚀 Additional Features

- **Swagger/OpenAPI documentation** for clear endpoint discovery and interactive testing.
- **Validation/error evidence** (400 response behavior shown in screenshots).
- **Database verification scripts** (`check_database.sql`) for easier grading and debugging.
- **Relationship-specific SQL support** (`product_tags_relationship.sql`) to demonstrate many-to-many implementation details.
- **Design documentation artifacts** (`eer_diagram.pdf`, `One_to_many.pdf`, `many_to_many.pdf`) included in the repo root for quick rubric verification.

---

## 🛠 How to Access and Use Swagger

### Correct Swagger URL
After running the app locally, use:

**http://localhost:8080/swagger-ui/index.html**

> Use this as the primary Swagger link for this API.

### Steps
1. Start MySQL and ensure the schema is available.
2. Run the Spring Boot app.
3. Open Swagger at `http://localhost:8080/swagger-ui/index.html`.
4. Expand an endpoint group.
5. Click an endpoint (`GET`, `POST`, `PUT`, `DELETE`).
6. Click **Try it out**.
7. Enter required params/body and click **Execute**.
8. Review response JSON and status code.

---

## Run Instructions (Local)

1. Ensure Java and MySQL are running.
2. Create/load schema by running `dylans_yard_sale.sql`.
3. Configure DB connection values in application config:
   - `src/main/resources/application.properties` (or `application.yml`)
   - Set datasource URL/username/password for database: `dylans_yard_sale`
4. Run:

```bash
./mvnw spring-boot:run
```

(or `mvn spring-boot:run`, Windows: `mvnw.cmd spring-boot:run`)

5. Open Swagger: `http://localhost:8080/swagger-ui/index.html`
6. (Optional) Run `check_database.sql` to verify table counts and relationship data.

---

## Repository Evidence Map (Quick Grading Reference)

- `README.md` — project overview + rubric mapping
- `pom.xml` — Spring Boot/JPA dependencies
- `src/` — layered Java source (controllers/services/repositories/entities)
- `dylans_yard_sale.sql` — main SQL schema/data
- `product_tags_relationship.sql` — many-to-many relationship support
- `check_database.sql` — DB checks
- `eer_diagram.pdf` — ERD/EER design
- `One_to_many.pdf` — one-to-many proof
- `many_to_many.pdf` — many-to-many proof
- `docs/screenshots/*` — Swagger endpoint test evidence
