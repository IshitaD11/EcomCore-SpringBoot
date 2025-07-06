# PaymentService

The **PaymentService** manages payment processing for orders in the EcomCore-Microservices platform. It integrates with Stripe and Razorpay to generate payment links and process payments.

## Features

- Generates payment links for orders
- Integrates with Stripe and Razorpay APIs
- Communicates with OrderService and is discoverable via Eureka

## Technologies

- Spring Boot
- Stripe API ([docs](https://docs.stripe.com/api/payment-link/create?lang=java))
- Razorpay API ([docs](https://razorpay.com/docs/api/payments/payment-links/create-standard/))
- Eureka Client
- JUnit (Testing)

## Getting Started

### Prerequisites

- Java 17+
- Maven
- Eureka ServiceDiscovery running
- API keys for Stripe and Razorpay

### Configuration

Set these in `application.properties` or your `.env` file:

```
STRIPE_API_KEY=sk_test_xxx
RAZORPAY_KEY=rzp_test_xxx
RAZORPAY_SECRET=your_secret
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://localhost:8761/eureka/
```

### Running the Service

```bash
mvn spring-boot:run
```

## Useful Links

- [Stripe Payment Links](https://docs.stripe.com/api/payment-link/create?lang=java)
- [Razorpay Payment Links](https://razorpay.com/docs/api/payments/payment-links/create-standard/)
