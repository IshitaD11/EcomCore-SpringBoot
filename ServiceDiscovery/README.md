# ServiceDiscovery

The **ServiceDiscovery** microservice provides Eureka-based service discovery for all microservices in the EcomCore-Microservices platform, enabling dynamic registration and lookup.

## Features

- Runs a Eureka server
- Enables microservices to register and discover each other

## Technologies

- Spring Boot
- Eureka Server

## Getting Started

### Prerequisites

- Java 17+
- Maven

### Configuration

Set the Eureka server port and other relevant settings in `application.properties` (default port is 8761):

```
SERVER_PORT=8761
```

### Running the Service

```bash
mvn spring-boot:run
```

- Access the Eureka dashboard at [http://localhost:8761](http://localhost:8761)

## Useful Links

- [Spring Cloud Netflix Eureka](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-eureka-server.html)
