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
	repository URL: **url**: https://github.com/ratchet91/inventory-management
2. Navigate to the project directory.
3. Run the following command to build and run the application:

mvn spring-boot:run

The application will start on http://localhost:9091 by default. -> as it is configured in application.properties file.


## API Endpoints

## 

### Add Inventory
### Get All Inventory Products

- **URL:** `/inventory/getAllProducts`
- **Method:** `GET`
- **Request Body:**
  
  
Response:
Success: HTTP 200 OK
- **Response Body:**
{
    "products": [
        {
            "productName": "Test123",
            "quantity": 130
        },
        {
            "productName": "Test1223",
            "quantity": 100
        }
    ],
    "count": 2
}
Error: HTTP 400 Bad Request - Error message.

### Get Inventory Product By Id

- **URL:** `/inventory/getProductsById/{id}`
- **Method:** `GET`
- **Request Body:**
  
  
Response:
Success: HTTP 200 OK
- **Response Body:**
{
    "productName": "Test123",
    "quantity": 130
}
Error: HTTP 400 Bad Request - Error message.

### Delete Inventory Product By Id
-**Note** - Only if quantity of inventory product is 0, then only we can delete it otherwise it will not allow.

- **URL:** `/inventory/deleteProductById/{id}`
- **Method:** `DELETE`
- **Request Body:**
  
  
Response:
Success: HTTP 200 OK
- **Response Body:**

## ADD Product to Inventory
-**Note** - Only Unique products can be added again, Product with same name cannot be added.
- **URL:** `/inventory/product/add`
- **Method:** `POST`
- **Request Body:**
  {
    "productName": "Test123",
    "quantity": 10
  }
  
Response:
Success: HTTP 200 OK
- **Response Body:**
 {
    "productName": "Test123",
    "quantity": 10
 }

 Error: HTTP 400 Bad Request - Error message.


## Deduct Quantity to Existing Product
-**Note** - Will throw error if:
  1. Product not present.
  2. Quantity to DEDUCT is less than 0.
  3. Quantity to DEDUCT is more than existing.

- **URL:** `/inventory/product/deductQuantity`
- **Method:** `POST`
- **Request Body:**
  {
    "id": 1,
    "quantity": 10,
    "transactionType": "ADDITION"
}
  
Response:
Success: HTTP 200 OK
- **Response Body:**
 {
    "productName": "Test123",
    "quantity": 100
 }

 Error: HTTP 400 Bad Request - Error message.

## ADD Quantity to Existing Product
-**Note** - Will throw error if:
  1. Product not present.
  2. Quantity to ADD is less than 0.


- **URL:** `/inventory/product/deductQuantity`
- **Method:** `POST`
- **Request Body:**
  {
    "id": 1,
    "quantity": 10,
    "transactionType": "ADDITION"
}
  
Response:
Success: HTTP 200 OK
- **Response Body:**
 {
    "productName": "Test123",
    "quantity": 80
 }

 Error: HTTP 400 Bad Request - Error message.


Database Schema:
The application uses a relational database to track inventory changes. The schema includes the following table:

CREATE TABLE `inventory_transaction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `quantity_change` int(11) DEFAULT NULL,
  `timestamp` timestamp NULL DEFAULT current_timestamp(),
  `transaction_type` enum('DEDUCTION','ADDITION') DEFAULT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6qmmodpse2tjsmxympifdfug5` (`item_id`),
  CONSTRAINT `FK6qmmodpse2tjsmxympifdfug5` FOREIGN KEY (`item_id`) REFERENCES `inventory_item` (`id`)
)
The above table will store transaction information i.e in our case an entry with respect to addition or deduction.


CREATE TABLE `inventory_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(255) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_21us6559nk34qxe6vwxbk7117` (`product_name`)
) 

This will store Product details like 
product/item id
product/item name and
Quantity present
