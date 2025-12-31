📌 #E-commerce Order Platform (Microservices)
Project Overview

This project is a simplified e-commerce backend built using Spring Boot and a microservices architecture.
It demonstrates how an Order Service coordinates with Product and Payment services to create and manage orders, while handling both business and technical failures cleanly.

The current focus of this project is backend design, service communication, and error handling rather than UI.

🧩 Architecture & Services

The system consists of three independent Spring Boot services:

Order Service

    Accepts order requests

    Validates product availability

    Initiates payment

    Manages order lifecycle and status

Product Service

    Provides product availability information

    Simulates inventory behavior

Payment Service

    Simulates payment processing

    Randomly returns success or failure

All services communicate synchronously using REST.

🔄 End-to-End Order Flow

Client sends a request to POST /orders

Order Service validates product availability via Product Service

    If product is unavailable, the request fails with a business error

    If product is available, Order Service creates an order with status CREATED

Order Service calls Payment Service

Based on payment response:

    Order is marked CONFIRMED on success

    Order is marked FAILED on payment failure

Order response is returned to the client

📡 API Summary

Order Service

POST /orders – Place a new order

GET /orders/{id} – Fetch order by ID

GET /orders/health – Health check

Product Service

GET /products/{productId} – Fetch product availability

Payment Service

POST /payments – Process payment

⚠️ Error Handling Strategy

The system distinguishes between different failure types:

404 Not Found
Returned when an order does not exist

409 Conflict
Returned when a product exists but is unavailable (business conflict)

503 Service Unavailable
Returned when a dependent service (Product or Payment) is unavailable

This separation helps keep API responses meaningful and predictable.

🧠 Design Decisions & Trade-offs

Request and response DTOs are kept separate to avoid tight coupling

Order creation returns HTTP 201 even when payment fails, since the order resource is still created and its final state is represented via status

External service failures are handled explicitly instead of bubbling up as generic 500 errors

URLs and service behavior are kept simple for clarity and extensibility

▶️ How to Run Locally

Start Product Service (port 8081)

Start Payment Service (port 8082)

Start Order Service (port 8080)

Use Postman or TalendAPI to test APIs

H2 in-memory database is used for persistence in Order Service.

🚀 Possible Future Enhancements

Replace RestTemplate with WebClient or Feign

Add circuit breakers (Resilience4j)

Add authentication and authorization

Add Docker and CI/CD pipeline

Improve payment retry and compensation logic

Add a User service and Notification service

Add a frontend UI in React
