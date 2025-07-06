# EcomCore-Microservices

_EcomCore-Microservices_ is a backend e-commerce platform composed of independent Spring Boot microservices. It provides robust, scalable APIs for user authentication, product management, order handling, payment processing, and email notifications—designed for integration with any modern frontend.

> **Note:** This repository contains only the backend microservices. No frontend/UI is included.

---

## Architecture Overview

The platform consists of the following microservices:

- **UserAuthenticationService**: Handles user registration, login, authentication (JWT), and role management (Admin, Customer, Seller).
- **ProductCatalogService**: Manages products. Admins and Sellers can add products. Integrates with Fakestoreapi and uses Redis for caching product data.
- **OrderService**: Manages order placement, fetching product details from ProductCatalogService.
- **PaymentService**: Integrates with Stripe and Razorpay to process payments and generate payment links.
- **EmailService**: Sends welcome emails to new users, triggered asynchronously via Kafka.
- **ServiceDiscovery**: Eureka server for dynamic service discovery.
- **JUnit Tests**: Each service includes JUnit-based tests for reliability.

---

## Features

- User sign up, login, authentication with JWT (OAuth ready)
- Role-based access: Admin, Seller, Customer
- Product CRUD with caching and external API sync
- Order placement with product detail aggregation
- Payment via Stripe and Razorpay
- Welcome emails on registration (Kafka event-driven)
- Eureka-based service discovery
- MySQL for persistent data, Redis for caching
- Kafka for asynchronous communication
- Comprehensive JUnit test coverage

---

## Microservice Structure

```
.
├── UserAuthenticationService
├── ProductCatalogService
├── OrderService
├── PaymentService
├── EmailService
├── ServiceDiscovery
```

---

## Integration & Setup Instructions

### Prerequisites

- Windows 10/11 with [WSL2 (Windows Subsystem for Linux)](https://learn.microsoft.com/en-us/windows/wsl/install)
- Java 17+ (install in WSL2)
- Maven (install in WSL2)
- MySQL (install in WSL2)
- Redis (install in WSL2)
- Kafka (install in WSL2)
- Docker (optional, for containers)
- ngrok (for tunneling, if needed)

### 1. Install WSL2

Follow the official Microsoft guide to install [WSL2](https://learn.microsoft.com/en-us/windows/wsl/install).  
All further installations should be done inside your WSL2 environment.

### 2. Install Dependencies in WSL2

- **Java 17:**  
  `sudo apt update && sudo apt install openjdk-17-jdk`

- **Maven:**  
  `sudo apt install maven`

- **MySQL:**  
  `sudo apt-get install mysql-server

- **Redis:**  
  [Install Redis on Windows/Linux](https://redis.io/docs/latest/operate/oss_and_stack/install/archive/install-redis/install-redis-on-windows/)

- **Kafka:**  
  [Install Apache Kafka](https://learn.conduktor.io/kafka/how-to-install-apache-kafka-on-windows/)

- **ngrok:**  
  [ngrok for Windows](https://ngrok.com/downloads/windows) (for exposing local endpoints if testing webhooks or callbacks)


### 3. Creating a .env File for Environment Variables and Running Services in WSL2

  #### 1. Open Your WSL Terminal
  
  For example, open Ubuntu from your Start menu.
  
  ---
  
  #### 2. Navigate to Your Project Directory
  
  ```bash
  cd ~/your-project-folder
  ```
  
  ---
  
  #### 3. Create a .env File
  
  ```bash
  nano .env
  ```
  
  ---
  
  #### 4. Add Your Environment Variables
  
  Add each variable on a separate line, e.g.:
  
  ```
  DB_URL=jdbc:mysql://localhost:3306/mydb
  DB_USER=root
  DB_PASSWORD=yourpassword
  JWT_SECRET=mysecretkey
  ```
  
  - Do **not** use quotes or the `export` keyword.
  - Save (Ctrl + O → Enter) and exit (Ctrl + X) in Nano.
  
  ---
  
  
  #### 5. Start Zookeeper and Kafka (in WSL)
  
  Open a new terminal window for each process.
  
  - **Start Zookeeper:**
    ```bash
    bin/zookeeper-server-start.sh config/zookeeper.properties
    ```
  
  - **Start Kafka Broker:**
    ```bash
    bin/kafka-server-start.sh config/server.properties
    ```
  
  (Refer to [Kafka Install Guide](https://learn.conduktor.io/kafka/how-to-install-apache-kafka-on-windows/) for more details.)
  
  ---
  
  #### 6. Load Environment Variables into Your Shell and Run Each Microservice
  
  To export all variables from your `.env` file into your current terminal session. Set environment variable for each microservice
  
  ```bash
  set -o allexport; source .env; set +o allexport
  ```
  
  This makes the variables available to any command you run in this shell window.
  
  ---
  
  Run each microservice in a separate WSL2 terminal window (one per service):
  
  ```bash
  cd ServiceDiscovery
  mvn spring-boot:run
  ```
  ```bash
  cd ../UserAuthenticationService
  mvn spring-boot:run
  ```
  ```bash
  cd ../ProductCatalogService
  mvn spring-boot:run
  ```
  ```bash
  cd ../OrderService
  mvn spring-boot:run
  ```
  ```bash
  cd ../PaymentService
  mvn spring-boot:run
  ```
  ```bash
  cd ../EmailService
  mvn spring-boot:run
  ```
  
  ---
  
  **Tip:** Always load your `.env` variables in each terminal window before running a service.
  
  ---


## Useful Documentation & Links

- **Fakestore API:** [https://fakestoreapi.com/docs](https://fakestoreapi.com/docs)
- **Spring OAuth Authorization Server:** [https://docs.spring.io/spring-authorization-server/reference/getting-started.html](https://docs.spring.io/spring-authorization-server/reference/getting-started.html)
- **Razorpay Payment Links:** [https://razorpay.com/docs/api/payments/payment-links/create-standard/](https://razorpay.com/docs/api/payments/payment-links/create-standard/)
- **Stripe Payment Links:** [https://docs.stripe.com/api/payment-link/create?lang=java](https://docs.stripe.com/api/payment-link/create?lang=java)
- **ngrok:** [https://ngrok.com/downloads/windows](https://ngrok.com/downloads/windows)
- **Kafka (Install & Setup):** [https://learn.conduktor.io/kafka/how-to-install-apache-kafka-on-windows/](https://learn.conduktor.io/kafka/how-to-install-apache-kafka-on-windows/)
- **Redis (Install & Setup):** [https://redis.io/docs/latest/operate/oss_and_stack/install/archive/install-redis/install-redis-on-windows/](https://redis.io/docs/latest/operate/oss_and_stack/install/archive/install-redis/install-redis-on-windows/)

---

## API Documentation

Each service exposes REST APIs. Refer to each service’s README or controller files for detailed endpoints.  
Swagger/OpenAPI can be added for interactive docs.

---

## License

This project is open-source and available under the MIT License.

---

## Contributions

Contributions are welcome! Please open issues or pull requests for improvements or bug fixes.
