# 📨 MS Delivery Courier Service

---

## 🧭 Table of Contents

* [Overview](#overview)
* [Core Responsibilities](#core-responsibilities)
* [Architecture](#architecture)
* [Technology Stack](#technology-stack)
* [Getting Started](#getting-started)

  * [Installation](#installation)
  * [Running the Service](#running-the-service)
* [Configuration](#configuration)
* [API Reference](#api-reference)
* [Model Definitions](#model-definitions)
* [Security](#security)
* [Swagger API Docs](#swagger-api-docs)
* [Docker Deployment](#docker-deployment)
* [Troubleshooting](#troubleshooting)
* [Contributing](#contributing)
* [License](#license)

---

## 📦 Overview

The **MS Delivery Courier Service** is a lightweight REST microservice that exposes courier order endpoints for internal or external clients (such as courier mobile apps).
It acts as an intermediary between the courier interface and the core **Order Management Service**, using **Spring Cloud OpenFeign** to forward and manage communication.

This design isolates courier-specific API contracts while maintaining a clean separation of business logic, which resides in the backend service.

---

## 🎯 Core Responsibilities

* Provide REST endpoints for courier order management (change state, history, and detail).
* Delegate business operations to `CourierOrderClient` via Feign integration.
* Validate and secure courier requests.
* Cache authentication tokens using Redis.
* Handle internationalized responses and exception management.

---

## 🏗️ Architecture

```plaintext
 ┌──────────────────────────────┐
 │   Courier Mobile App / UI    │
 └──────────────┬───────────────┘
                │  (REST API)
 ┌──────────────┴──────────────────────────┐
 │       MS Delivery Courier Service       │
 │------------------------------------------│
 │  @RestController (CourierController)     │
 │  └── Delegates to CourierOrderClient     │
 │  └── Uses Feign to call Order Service    │
 │  └── Token caching with Redis            │
 │  └── Security & Localization             │
 └──────────────┬──────────────────────────┘
                │
        ┌───────┴────────┐
        │ Order Service  │
        └────────────────┘
```

---

## 🧰 Technology Stack

| Layer             | Technology                    |
| ----------------- | ----------------------------- |
| **Language**      | Java 17+                      |
| **Framework**     | Spring Boot 3.x               |
| **API Client**    | Spring Cloud OpenFeign        |
| **Cache**         | Redis                         |
| **Security**      | Spring Security + JWT         |
| **Validation**    | Jakarta Validation (`@Valid`) |
| **Documentation** | Swagger / Springdoc OpenAPI   |
| **Build Tool**    | Gradle 8+                     |

---

## 🚀 Getting Started

### Installation

```bash
# Clone the repository
git clone https://github.com/guavapay/ms-delivery-courier.git
cd ms-delivery-courier

# Build the project
./gradlew clean build
```

### Running the Service

```bash
# Run with Gradle
./gradlew bootRun

# Or run the packaged JAR
java -jar build/libs/ms-delivery-courier-*.jar
```

Default port: **8082**

---

## ⚙️ Configuration

Main configuration file:
`src/main/resources/application.yml`

```yaml
server:
  port: 8082

spring:
  redis:
    host: localhost
    port: 6379

feign:
  client:
    config:
      default:
        connectTimeout: 5000
        readTimeout: 5000
```

---

## 📘 API Reference

All endpoints are under the base path:

```
/courier/order
```

### 1. Change Order State

**POST** `/courier/order/change/state`

Updates the current delivery state of an order.

**Request Body**

```json
{
  "orderId": 1023,
  "newState": "IN_TRANSIT",
  "comment": "Courier picked up the package"
}
```

**Response**

```json
{
  "status": "success",
  "message": "Order state updated successfully"
}
```

---

### 2. Retrieve Order History

**POST** `/courier/order/history`

Returns a list of historical orders for a courier.

**Request Body**

```json
{
  "courierId": 501,
  "startDate": "2025-10-01T00:00:00",
  "endDate": "2025-10-05T23:59:59",
  "status": "DELIVERED"
}
```

**Response**

```json
[
  {
    "orderId": 1023,
    "state": "DELIVERED",
    "deliveryTime": "2025-10-05T14:35:00"
  }
]
```

---

### 3. Get Order Details

**GET** `/courier/order/detail?id={parcelId}`

Retrieves detailed information about a single order.

**Example Request**

```
GET /courier/order/detail?id=12345
```

**Response**

```json
{
  "orderId": 12345,
  "courierId": 501,
  "state": "DELIVERED",
  "destination": "123 Elm Street, London"
}
```

---

## 📦 Model Definitions

| Model                       | Description                                                     |
| --------------------------- | --------------------------------------------------------------- |
| **OrderStateChangeRequest** | Request object to update an order’s delivery state              |
| **CourierOrderFilter**      | Filters for retrieving order history                            |
| **CourierOrderHistoryDto**  | Detailed representation of a courier’s order or delivery record |

---

## 🔐 Security

* JWT authentication is enforced on all endpoints.
* Tokens are validated and cached via Redis (`TokenStorage`).
* Implemented filters: `SecurityFilter` and `FeignInterceptor`.

**Header Example:**

```http
Authorization: Bearer <jwt_token>
```

---

## 🧭 Swagger API Docs

Swagger UI available at:

```
http://localhost:8082/swagger-ui.html
```

OpenAPI JSON spec:

```
http://localhost:8082/v3/api-docs
```

---

## 🐳 Docker Deployment

### Build

```bash
docker build -t guavapay/ms-delivery-courier .
```

### Run

```bash
docker run -d -p 8082:8082 guavapay/ms-delivery-courier
```

---

## 🧩 Troubleshooting

| Issue                    | Cause                      | Fix                                  |
| ------------------------ | -------------------------- | ------------------------------------ |
| `401 Unauthorized`       | Missing/invalid JWT        | Provide a valid token                |
| `Feign timeout`          | Target service unreachable | Check network or Feign client config |
| `Redis connection error` | Redis not running          | Start Redis or update host config    |

---

## 🤝 Contributing

We welcome pull requests and feature suggestions!

1. Fork the repository
2. Create a new feature branch
3. Submit a PR with clear description and test coverage

---

## 📜 License

Licensed under the **MIT License**.
See the [LICENSE](LICENSE) file for details.

---
