# ProductCatalogService

The **ProductCatalogService** manages products in the EcomCore-Microservices platform. It supports CRUD operations for products, caches product data from Fakestoreapi using Redis, and is accessible by the OrderService.

## Features

- CRUD operations for products (Admin/Seller)
- Fetches and caches public product data from Fakestoreapi
- Uses Redis for caching
- Eureka Client for service discovery

## Technologies

- Spring Boot
- MySQL
- Redis
- Eureka Client
- JUnit (Testing)

## Getting Started

### Prerequisites

- Java 17+
- Maven
- MySQL running
- Redis running ([Install Guide](https://redis.io/docs/latest/operate/oss_and_stack/install/archive/install-redis/install-redis-on-windows/))
- Eureka ServiceDiscovery running

### Configuration

Set the following in `application.properties` or `.env`:

```
spring.datasource.url=DB_URL
spring.datasource.username=DB_username
spring.datasource.password=DB_PASSWORD
spring.data.redis.port=6379
spring.data.redis.host=localhost
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
eureka.client.service-url.defaultZone=http://localhost:8761/eureka
```

### Running the Service

```bash
mvn spring-boot:run
```

## Useful Links

- [Fakestore API](https://fakestoreapi.com/docs)
- [Redis Install Guide](https://redis.io/docs/latest/operate/oss_and_stack/install/archive/install-redis/install-redis-on-windows/)
