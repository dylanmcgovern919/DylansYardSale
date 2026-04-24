# Dylan’s Yard Sale API

This is my Spring Boot Final Project for a yard-sale style shop where I resell **clothes, records, and comics**.  
I built this as a REST API project using Java, Spring Boot, and MySQL. It covers all assignment requirements and includes a few extra features I found useful.

---

## What this app is

This app is a backend for managing:

- **Products** (clothing, records, comics)
- **Tags** (like Vintage, Punk, Hip-Hop, etc.)
- **Orders** with status + line items + quantity

It supports full CRUD on core resources and includes relationship handling (products ↔ tags, orders ↔ items ↔ products).

---

## Core project idea (in plain terms)

I run a resale-style catalog where I can post stuff like:

- vintage clothing
- records by genre
- comics by publisher/tag

Then I can create orders with multiple items and quantities, track status, and manage product tags through endpoints.

---

## How I meet the prompt requirements

## ✅ Requirement: Spring Boot REST API with proper structure
I used a clean layered setup:
- `controller` for endpoints
- `model` for entities
- `repository` for data access
- `config` for startup seed logic
- `exception` for centralized error handling

---

## ✅ Requirement: CRUD endpoints
I implemented CRUD controllers for:
- **Products** (`/api/products`)
- **Tags** (`/api/tags`)
- **Orders** (`/api/orders`)

Each has GET/POST/PUT/DELETE patterns and uses proper response codes.

---

## ✅ Requirement: Database + JPA entities
I used JPA entities with relationship mapping:
- `Product` ↔ `Tag` = **Many-to-Many**
- `Order` → `OrderItem` = **One-to-Many**
- `OrderItem` → `Product` = **Many-to-One**

Also used enums for constrained values:
- `ProductCategory` (CLOTHING, RECORD, COMIC)
- `OrderStatus` (PROCESSING, SHIPPED, COMPLETED)

---

## ✅ Requirement: Validation
I added input validation with annotations:
- `@NotBlank` for tag names


