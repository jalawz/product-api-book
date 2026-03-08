# Product API

[![CI](https://github.com/jalawz/product-api-book/actions/workflows/ci.yml/badge.svg)](https://github.com/jalawz/product-api-book/actions/workflows/ci.yml)

REST API for product catalog data built with Spring Boot, JPA, Flyway, and PostgreSQL.

## Tech Stack

- Java 17
- Spring Boot 2.7.5
- Spring Web
- Spring Data JPA
- Flyway
- PostgreSQL
- H2 (tests)
- Lombok
- OpenAPI/Swagger (`springdoc-openapi-ui`)

## Runtime Configuration

From `src/main/resources/application.yml`:

- App port: `8081`
- DB URL: `${DB_URL:jdbc:postgresql://localhost:5432/dev}`
- DB user/password: `${DB_USER:postgres}` / `${DB_PASSWORD:postgres}`
- Flyway schema: `products`

You can override DB settings with environment variables:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/dev
export DB_USER=postgres
export DB_PASSWORD=postgres
```

## API Endpoints

Base URL:

```text
http://localhost:8081/products
```

- `GET /products`
- `GET /products/categories/{categoryId}`
- `GET /products/{productIdentifier}`
- `POST /products`
- `DELETE /products/{id}`

OpenAPI docs:

```text
http://localhost:8081/swagger-ui/index.html
http://localhost:8081/v3/api-docs
```

## Postman

Import the files below in Postman:

- `postman/product-api-book.postman_collection.json`
- `postman/product-api-book.postman_environment.json`

Then select the `product-api-book-local` environment and run requests in this order:

1. `Create product`
2. `Get all products`
3. `Get products by category`
4. `Get product by identifier`

## Build and Test

```bash
./mvnw clean verify
```

## Quality and Security Gates

- CI runs `./mvnw clean verify`.
- JaCoCo enforces minimum **80% line coverage** for business rules in `service` package.
- CAST check runs SpotBugs (`spotbugs:check`) in pipeline.
- SAST runs GitHub CodeQL in `.github/workflows/sast.yml`.

## Suggested Next Improvements

1. Add integration tests for controller endpoints with `MockMvc`.
2. Add pagination and sorting for list endpoints.
3. Add endpoint-level OpenAPI annotations with richer examples.
