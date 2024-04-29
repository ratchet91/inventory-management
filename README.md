# Inventory Management API

Description

This project is an inventory management system implemented in Java with Spring Boot. It provides a RESTful API for handling inventory operations such as deducting and adding inventory quantities.
It and ensuring data consistency and integrity. It provides a RESTful API for performing these operations and manages inventory data in a relational database.

##Feature:
Features
1. Add products to the inventory
2. Deduct quantities when fulfilling orders
3. Add quantities when restocking products or processing returns
4. Track inventory transactions (additions and deductions)
5. Ensure data consistency and integrity during inventory updates
6. Support for concurrent inventory transactions
7. API documentation for endpoints and request/response formats

##Technologies Used
1. Java
2. Spring Boot
3. Spring Data JPA
4. MySQL (or your preferred relational database)
5. Maven (for dependency management)
6. JUnit (for unit testing)
7. Mockito (for mocking dependencies in tests)

## Getting Started

To run this application locally, you need to have Java and Maven installed on your machine.

1. Clone this repository to your local machine.
2. Navigate to the project directory.
3. Run the following command to build and run the application:

mvn spring-boot:run

The application will start on http://localhost:9091 by default. -> as it is configured in application.properties file.


## API Endpoints

### Add Inventory

- **URL:** `/api/inventory/deduct`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
    "productId": 1,
    "quantity": 5,
    "type": "DEDUCTION"
  }
  
Response:
Success: HTTP 200 OK - "Inventory deducted successfully."
Error: HTTP 400 Bad Request - Error message.

### Add Inventory

- **URL:** `/api/inventory/deduct`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
    "productId": 1,
    "quantity": 5,
    "type": "DEDUCTION"
  }
  
Response:
Success: HTTP 200 OK - "Inventory added successfully."
Error: HTTP 400 Bad Request - Error message.

Database Schema
The application uses a relational database to track inventory changes. The schema includes the following table:

CREATE TABLE inventory_transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    transaction_type ENUM('DEDUCTION', 'ADDITION') NOT NULL,
    quantity INT NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);