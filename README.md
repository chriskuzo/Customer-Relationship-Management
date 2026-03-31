# Customer Relationship Management Backend

Spring Boot backend for customer, vendor, order, and product catalog management with JWT authentication and role-based access control.

## Tech Stack

- Java 21
- Spring Boot 3.5.7
- Spring Security (JWT)
- Spring Data JPA (Hibernate)
- PostgreSQL
- Swagger / OpenAPI (`springdoc-openapi`)
- Maven

## Project Structure

- `auth`: registration, login, JWT, roles
- `customer`: customer CRUD
- `vendor`: vendor CRUD
- `order`: order CRUD
- `catalog`: product CRUD and search
- `config`: security, CORS, global exception handling, Swagger

## Prerequisites

- Java 21+
- Maven 3.9+ (or use `mvnw`)
- PostgreSQL running locally

## Configuration

Main application config is in `src/main/resources/application.properties`.

Default local database values:

- URL: `jdbc:postgresql://localhost:5432/mahwidb`
- Username: `postgres`
- Password: `newpassword`
- Port: `8080`

JWT settings:

- `jwt.secret`
- `jwt.expiration-ms` (default 24h)

Update these values for your environment before deploying.

## Run Locally

### 1) Clone and enter project

```bash
git clone https://github.com/chriskuzo/Customer-Relationship-Management.git
cd Customer-Relationship-Management
```

### 2) Start the app

Windows:

```bash
./mvnw.cmd spring-boot:run
```

macOS/Linux:

```bash
./mvnw spring-boot:run
```

Or with system Maven:

```bash
mvn spring-boot:run
```

### 3) Run tests

```bash
./mvnw test
```

## API Documentation

When the server is running:

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Authentication

Public endpoints:

- `POST /api/auth/register`
- `POST /api/auth/login`

All other API routes require a Bearer token.

Use this header:

```http
Authorization: Bearer <JWT_TOKEN>
```

## Roles

Roles used by the API:

- `ROLE_ADMIN`
- `ROLE_DAF`
- `ROLE_SALES`
- `ROLE_CUSTOMER`

Role seed script is available in `src/main/resources/db/migration/V1__seed_roles.sql`.

## Main Endpoints

### Auth

- `POST /api/auth/register`
- `POST /api/auth/login`

### Customers (`SALES`, `ADMIN`)

- `GET /api/customers`
- `GET /api/customers/{id}`
- `POST /api/customers`
- `PUT /api/customers/{id}`
- `DELETE /api/customers/{id}`

### Vendors (`DAF`, `ADMIN`)

- `GET /api/vendors`
- `GET /api/vendors/{id}`
- `POST /api/vendors`
- `PUT /api/vendors/{id}`
- `DELETE /api/vendors/{id}`

### Orders (`DAF`, `ADMIN`)

- `GET /api/orders`
- `GET /api/orders/{id}`
- `POST /api/orders`
- `PUT /api/orders/{id}`
- `DELETE /api/orders/{id}`

### Catalog Products (`DAF`, `ADMIN`)

- `GET /api/catalog/products/all`
- `GET /api/catalog/products`
- `GET /api/catalog/products/{id}`
- `POST /api/catalog/products`
- `PUT /api/catalog/products/{id}`
- `DELETE /api/catalog/products/{id}`

## Example Auth Requests

Register:

```json
{
  "identifier": "admin",
  "password": "secret123",
  "email": "admin@example.com",
  "role": "ADMIN"
}
```

Login:

```json
{
  "identifier": "admin",
  "password": "secret123"
}
```

## Notes

- The current config sets `spring.flyway.enabled=false`.
- `spring.jpa.hibernate.ddl-auto=update` is currently enabled.
- For production, prefer managed migrations and stricter DB/versioning settings.
