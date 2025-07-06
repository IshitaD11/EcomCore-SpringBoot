# EmailService

The **EmailService** is a Spring Boot microservice dedicated to sending transactional emails (e.g., welcome emails) in the EcomCore-Microservices platform. It listens for Kafka events and sends emails accordingly.  

## Features

- Listens to Kafka topics for user registration events
- Sends welcome emails to new users
- Configurable SMTP support

## Technologies

- Spring Boot
- Apache Kafka
- JavaMail (SMTP)

## Getting Started

### Prerequisites

- Java 17+
- Maven
- Kafka running (see [Kafka Install Guide](https://learn.conduktor.io/kafka/how-to-install-apache-kafka-on-windows/))

### Configuration

Set the following in `application.properties` or your `.env` file:

```
SPRING_KAFKA_BOOTSTRAP_SERVERS=localhost:9092
MAIL_HOST=smtp.example.com
MAIL_PORT=587
MAIL_USERNAME=your_email@example.com
MAIL_PASSWORD=your_email_password
```

### Running the Service

```bash
mvn spring-boot:run
```

## Useful Links

- [JavaMail API](https://javaee.github.io/javamail/)
- [Kafka Quickstart](https://learn.conduktor.io/kafka/how-to-install-apache-kafka-on-windows/)
