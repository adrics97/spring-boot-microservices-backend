# Microservices Backend (Spring Boot + Gateway)

Backend project based on **Spring Boot microservices** with **Spring Cloud Gateway (WebFlux)**.
It includes **centralized JWT authentication**, **CORS configuration**, and **Swagger aggregation** for multiple services.

## Architecture

- **api-gateway (8080)**  
  Entry point for the system. Handles routing, JWT authentication, CORS and Swagger aggregation.

- **users-service (8081)**  
  User management and authentication service.  
  Uses PostgreSQL for persistence.

- **orders-service (8082)**  
  Orders API (development profile).

- **PostgreSQL (5432)**  
  Persistent database for `users-service`, using Docker volumes.

## Public vs Internal API Contract

- **Public contract (Gateway):**
    - `/users/**`
    - `/orders/**`

- **Internal contract (Services):**
    - `/api/users/**`
    - `/api/orders/**`

The OpenAPI specification is adapted so that Swagger documents the **public contract**, while services keep their internal routes.

## Requirements

- Docker
- Docker Compose

## Environment Variables

Create a `.env` file next to `docker-compose.yml`:

```env
JWT_SECRET=your_long_jwt_secret_here_min_32_chars
```

## Run the project 

```env
docker compose up --build
```

## API Documentation (Swagger)
Swagger UI is exposed through the Gateway:

http://localhost:8080/swagger-ui/index.html

## Basic Usage Flow

Authenticate using:

1. Authenticating using (`/api/auth/login`)
2. Copy the returned JWT token
3. Open Swagger UI and click Authorize
4. Paste the token using the format:
   Bearer <JWT_TOKEN>
5. Call protected endpoints such as:
    - `GET /users/{id}`
    - `POST /users`
    - `GET /orders/...`

## Persistence

PostgreSQL data is stored using a Docker volume.

Restarting containers does not remove data

⚠️ Running docker compose down -v will delete all persisted data

## Profiles

The project uses Spring profiles to separate environments:

dev
Uses an H2 in-memory database (local development and tests)

docker
Uses PostgreSQL (Docker environment)

The docker profile is automatically enabled when running with Docker Compose.

## Health Checks

All services expose health endpoints using Spring Boot Actuator.

Docker uses these endpoints to determine when services are ready to receive traffic.

## Testing

The project includes automated tests in users-service:

- Unit tests

- JUnit 5

- Mockito

- Service layer testing

- Integration tests

- Spring Boot Test

- MockMvc

- REST controller testing with an in-memory database

Run tests locally from users-service:

```env
./mvnw test
```

## Technical Decisions

- API Gateway as a single entry point

- Stateless microservices

- Centralized JWT authentication

- Dockerized services with persistent storage

- Health checks using Spring Boot Actuator

- Swagger aggregation at the Gateway level

- Clear separation between public and internal API contracts