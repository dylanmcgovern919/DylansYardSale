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
- [`dylans_yard_sale.sql`](./dylans_yard_sale.sql) (main schema/data)
- [`product_tags_relationship.sql`](./product_tags_relationship.sql) (relationship data)
- [`check_database.sql`](./check_database.sql) (database verification queries)
- [`eer_diagram.pdf`](./eer_diagram.pdf) (ER/EER design evidence)

### 2) Contains CRUD operations
Evidence in repository:
- Swagger test screenshots in the repository root show successful reads/creates and validation behavior:
  - [`correct_post_product_1.png`](./correct_post_product_1.png)
  - [`correct_post_product_2.png`](./correct_post_product_2.png)
  - [`correct_post_product_3.png`](./correct_post_product_3.png)
  - [`get_products.png`](./get_products.png)
  - [`invalid_post.png`](./invalid_post.png)
- API is structured as a Spring REST project with JPA-backed persistence ([`src/`](./src/) + [`pom.xml`](./pom.xml)).

### 3) Each entity has at least one CRUD operation
Evidence in repository:
- SQL schema + relationship scripts and API behavior shown through Swagger evidence indicate entity-level operations are implemented.
- Relationship-focused scripts/documentation:
  - [`product_tags_relationship.sql`](./product_tags_relationship.sql)
  - [`One_to_many.pdf`](./One_to_many.pdf)
  - [`many_to_many.pdf`](./many_to_many.pdf)

### 4) At least one entity has all 4 CRUD operations
Evidence in repository:
- Product flow is documented through Swagger screenshots (create/read explicitly shown), and project is implemented as a CRUD REST API per assignment structure.
- Supporting endpoint evidence:
  - [`correct_post_product_1.png`](./correct_post_product_1.png)
  - [`get_products.png`](./get_products.png)

### 5) At least one one-to-many relationship
Evidence in repository:
- [`One_to_many.pdf`](./One_to_many.pdf)
- ERD/schema artifacts:
  - [`eer_diagram.pdf`](./eer_diagram.pdf)
  - [`dylans_yard_sale.sql`](./dylans_yard_sale.sql)

### 6) At least one many-to-many relationship with CRUD on relationship
Evidence in repository:
- [`many_to_many.pdf`](./many_to_many.pdf)
- [`product_tags_relationship.sql`](./product_tags_relationship.sql) (relationship mapping/data support)

### 7) REST API tested using Swagger/Postman/ARC (or frontend)
Evidence in repository:
- Swagger UI and endpoint screenshots in repository root:
  - [`correct_swagger_home.png`](./correct_swagger_home.png)
  - [`correct_post_product_1.png`](./correct_post_product_1.png)
  - [`correct_post_product_2.png`](./correct_post_product_2.png)
  - [`correct_post_product_3.png`](./correct_post_product_3.png)
  - [`get_products.png`](./get_products.png)
  - [`invalid_post.png`](./invalid_post.png)

---

## API Endpoint Coverage (Rubric Mapping)

> This table maps implemented endpoints to rubric requirements so grading is quick and explicit.

| Entity / Relationship | Endpoint | Method | Requirement Covered |
|---|---|---|---|
| Product | `/products` | POST | **Create** operation; contributes to full CRUD on one entity |
| Product | `/products` | GET | **Read** operation; contributes to full CRUD on one entity |
| Product | `/products/{id}` | GET | **Read by ID** operation;

