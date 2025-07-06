# UserAuthenticationService

The **UserAuthenticationService** handles user registration, login, authentication, and role management for the EcomCore-Microservices platform. It emits Kafka events on registration for email notifications and uses JWT for authentication.

## Features

- User registration and login (Admin, Seller, Customer)
- JWT-based authentication
- Emits Kafka events on signup (for welcome emails)
- Eureka Client for service discovery

## Technologies

- Spring Boot
- MySQL
- JWT
- Kafka
- Eureka Client
- JUnit (Testing)

## Getting Started

### Prerequisites

- Java 17+
- Maven
- MySQL running
- Kafka running
- Eureka ServiceDiscovery running

### Configuration

Set the following in `application.properties` or your `.env` file:

```
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/ecom_users
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=yourpassword
JWT_SECRET=mysecretkey
SPRING_KAFKA_BOOTSTRAP_SERVERS=localhost:9092
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://localhost:8761/eureka/
```

### Running the Service

```bash
mvn spring-boot:run
```

### Running Tests

```bash
mvn test
```

## Useful Links

- [Spring Authorization Server (OAuth)](https://docs.spring.io/spring-authorization-server/reference/getting-started.html)
- [Kafka Quickstart](https://learn.conduktor.io/kafka/how-to-install-apache-kafka-on-windows/)
