# Microservices Backend (Spring Boot + Gateway)

Backend basado en microservicios con **Spring Boot** y **Spring Cloud Gateway (WebFlux)**.
Incluye **JWT auth centralizada en el Gateway**, **CORS**, y **Swagger agregado** para múltiples servicios.

## Arquitectura

- **api-gateway (8080)**: entrypoint, JWT, CORS, Swagger aggregator
- **users-service (8081)**: CRUD usuarios + auth/login (PostgreSQL)
- **orders-service (8082)**: endpoints de pedidos (perfil dev)
- **postgres (5432)**: persistencia de users-service (con volumen)

### Contrato público vs interno
- Público (Gateway): `/users/**`, `/orders/**`
- Interno (Servicios): `/api/users/**`, `/api/orders/**`
- OpenAPI se adapta para documentar el contrato público.

## Requisitos
- Docker + Docker Compose

## Variables de entorno
Crea un archivo `.env` junto a `docker-compose.yml`:

```env
JWT_SECRET=pon_aqui_un_secret_largo_de_32_chars_min
```

## Ejecutar el proyecto

Desde la raíz del repositorio:

```bash
docker compose up --build
```

## Cómo probar el sistema

Una vez levantados los servicios:

- Swagger UI (Gateway):
    - http://localhost:8080/swagger-ui/index.html

### Flujo básico
1. Registrar o loguear un usuario desde Swagger (`/api/auth/login`)
2. Copiar el JWT
3. Pulsar **Authorize** en Swagger e introducir el token
4. Probar endpoints protegidos:
    - `GET /users/{id}`
    - `POST /users`
    - `GET /orders/...`


## Persistencia de datos

Los datos del `users-service` se almacenan en PostgreSQL usando un **volumen Docker**.

- Reiniciar contenedores **no borra datos**
- ⚠️ Ejecutar `docker compose down -v` **sí elimina los datos**

## Perfiles

- `dev`: usa H2 en memoria (desarrollo local)
- `docker`: usa PostgreSQL (entorno Docker)

El perfil `docker` se activa automáticamente desde `docker-compose`.


## Decisiones técnicas

- API Gateway como punto de entrada único
- Seguridad (JWT) centralizada en el Gateway
- Servicios **stateless**
- Persistencia delegada a base de datos
- Swagger agregado y adaptado al contrato público


## Testing

El proyecto incluye tests automatizados en `users-service`:

- **Unit tests** con JUnit 5 y Mockito (capa de servicio)
- **Integration tests** con Spring Boot Test y MockMvc (capa REST)

Ejecutar tests:
```bash
./mvnw test
