# OrderService

The **OrderService** handles order creation and management in the EcomCore-Microservices platform. It fetches product information from ProductCatalogService and interacts with PaymentService for payment links.

## Features

- Place, view, and manage orders
- Fetch product details from ProductCatalogService
- Integrate with PaymentService for payment links
- Eureka Client for service discovery

## Technologies

- Spring Boot
- Eureka Client
- MySQL
- JUnit (Testing)

## Getting Started

### Prerequisites

- Java 17+
- Maven
- MySQL running
- Eureka ServiceDiscovery running

### Configuration

Set the following in `application.properties` or your `.env` file:

```
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/ecom_orders
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=yourpassword
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://localhost:8761/eureka/
```

### Running the Service

```bash
mvn spring-boot:run
```

## Useful Links

- [Eureka Service Discovery](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-eureka-server.html)
