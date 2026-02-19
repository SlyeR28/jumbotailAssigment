# E-Commerce Shipping Charge Estimator

## Overview

The **E-Commerce Shipping Charge Estimator** is a specialized B2B marketplace application designed to calculate shipping charges for delivering products to Kirana stores. It focuses on the unique logistics of a B2B model where products are shipped from warehouses to customers (Kirana stores) after being dropped off by sellers.

This application provides APIs to:
1.  Determine the nearest warehouse for a seller to drop off their products.
2.  Calculate shipping charges based on distance, transport mode, and delivery speed.
3.  Provide a comprehensive shipping cost estimation for a complete order cycle.

## Features

-   **Warehouse Discovery**: Automatically finds the closest warehouse for a seller based on location.
-   **Dynamic Logistics**: Calculates transport modes (Aeroplane, Truck, Mini Van) based on distance.
-   **Flexible Delivery Speeds**: Supports Standard (economical) and Express (faster, premium) delivery options.
-   **Accurate Cost Estimation**: specific rates per km/kg based on the mode of transport.
-   **Robust Error Handling**: Gracefully handles missing warehouses, invalid locations, and other edge cases.

## Tech Stack

-   **Language**: Java 21
-   **Framework**: Spring Boot (v4.1.0-SNAPSHOT)
-   **Database**: MySQL
-   **ORM**: Spring Data JPA
-   **Utilities**: Lombok, MapStruct
-   **Build Tool**: Maven

## Prerequisites

Before running the application or building it (which includes tests), ensure you have:

-   **Java 21 JDK** installed.
-   **MySQL Server** installed and running on port 3306 (default).
-   A database named `test` created in your MySQL instance (or update `application.yaml` to match your DB name).

## Configuration

The application uses MySQL as its primary database. You must configure your database connection in `src/main/resources/application.yaml` before running the app or tests.

Default configuration:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/shiping_backend
    username: root
    password: <YOUR_PASSWORD>
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
```

> **Important**:
> 1.  Update `username` and `password` to match your local MySQL installation.
> 2.  Ensure the database (e.g., `test`) exists.
> 3.  **Tests require a running database**: Since the project uses `@SpringBootTest` with the main configuration, the `mvn clean install` command will attempt to connect to the database. Ensure your MySQL server is running before building.

## Installation & Running

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/SlyeR28/jumbotailAssigment.git
    cd shipping-charge-estimator
    ```

2.  **Build the project**:

    Using the Maven Wrapper (recommended):
    -   **Windows**:
        ```bash
        .\mvnw.cmd clean install
        ```
    -   **Linux/Mac**:
        ```bash
        ./mvnw clean install
        ```

    Or if you have Maven installed globally:
    ```bash
    mvn clean install
    ```

3.  **Run the application**:

    Using the Maven Wrapper:
    -   **Windows**:
        ```bash
        .\mvnw.cmd spring-boot:run
        ```
    -   **Linux/Mac**:
        ```bash
        ./mvnw spring-boot:run
        ```

    Or with global Maven:
    ```bash
    mvn spring-boot:run
    ```

The application will start on **port 4000** (defined in `application.yaml`).

## API Documentation

The application exposes the following REST endpoints.

### 1. Get Nearest Warehouse

Finds the nearest warehouse where a seller can drop off items for a specific order.

-   **Endpoint**: `GET /api/v1/warehouse/nearest`
-   **Parameters**:
    -   `sellerId` (Long): The unique ID of the seller.
    -   `orderId` (Long): The unique ID of the order.

**Example Request:**
```http
GET http://localhost:4000/api/v1/warehouse/nearest?sellerId=123&orderId=456
```

**Example Response:**
```json
{
    "id": 789,
    "location": {
        "lat": 12.99999,
        "long": 37.923273
    }
}
```

### 2. Get Shipping Charge (Warehouse to Customer)

Calculates the shipping charge from a specific warehouse to a customer's location.

-   **Endpoint**: `GET /api/v1/shipping-charge`
-   **Parameters**:
    -   `warehouseId` (Long): ID of the source warehouse.
    -   `customerId` (Long): ID of the destination customer.
    -   `deliverySpeed` (String): `standard` or `express`.

**Example Request:**
```http
GET http://localhost:4000/api/v1/shipping-charge?warehouseId=789&customerId=456&deliverySpeed=standard
```

**Example Response:**
```json
{
    "shippingCharge": 150.00
}
```

### 3. Calculate Total Shipping Charges

A composite API that calculates the shipping charges for a seller and customer transaction in one go. It identifies the nearest warehouse and helps calculate the final cost.

-   **Endpoint**: `POST /api/v1/shipping-charge/calculate`
-   **Body**:
    ```json
    {
        "sellerId": 123,
        "customerId": 456,
        "deliverySpeed": "express"
    }
    ```

**Example Response:**
```json
{
    "shippingCharge": 180.00,
    "nearestWarehouse": {
        "id": 789,
        "location": { "lat": 12.99999, "long": 37.923273 }
    }
}
```

## Running Tests

To run the unit and integration tests:

```bash
mvn test
```

## Project Structure

```
src/main/java/com/jumbotail/shippingchargeestimator
├── controller      # REST Controllers (API Layer)
├── services        # Business Logic & Service Interfaces
├── repository      # Data Access Layer (JPA Repositories)
├── model           # JPA Entities (Database Models)
├── payloads        # DTOs (Request/Response objects)
├── exceptions      # Custom Exception Handling
├── mapper          # MapStruct Mappers
└── utils           # Utility classes (Constants, Enums)
```

## Logic Overview

-   **Transport Modes**:
    -   **Aeroplane**: Distance > 500 km (Rate: 1 Rs/km/kg)
    -   **Truck**: Distance > 100 km (Rate: 2 Rs/km/kg)
    -   **Mini Van**: Distance <= 100 km (Rate: 3 Rs/km/kg)
-   **Delivery Speed**:
    -   **Standard**: Base charge + Shipping charge.
    -   **Express**: Base charge + 1.2 Rs/kg extra + Shipping charge.

## Future Improvements

-   Implement specific caching mechanisms (Redis/Hazelcast) for frequently accessed warehouse data.
-   Add more granular transport logic based on real-time traffic or bulk orders.
-   Secure APIs using JWT or OAuth2.
