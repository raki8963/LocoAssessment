# Transaction Service

This Spring Boot application provides RESTful APIs to manage transactions. It allows creating, updating, retrieving, and calculating the sum of transactions stored in a MySQL database.

## Features
- Add Transactions: Create new transactions and set parent-child relationships.
- Update Transactions: Modify existing transactions and update related parent nodes.
- Sum Calculation: Compute total amounts for transactions and their child nodes efficiently.
## Project Structure
- src/main/java/org/example/service/TransactionService.java: Service class for handling transaction operations.
- src/main/java/org/example/repository/TransactionRepository.java: Repository interface for CRUD operations on transactions.
- src/main/java/org/example/entity/Transaction.java: Entity class representing a transaction.
- src/main/resources/application.properties: Configuration file for the application.


## Technologies Used

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **MySQL**
- **Maven**

## Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **MySQL 8.0+**

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/transaction-service.git
cd transaction-service
