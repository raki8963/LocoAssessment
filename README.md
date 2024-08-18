# Transaction Service

This Spring Boot application provides RESTful APIs to manage transactions. It allows creating, updating, retrieving, and calculating the sum of transactions stored in a MySQL database.

# Note:- Assuming Transaction Data Doesn't forms a loop.(It represents Tree Data Structure).


## Features
- Add Transactions: Create new transactions and set parent-child relationships.
- Update Transactions: Modify existing transactions and update related parent nodes.
- Sum Calculation: Compute total amounts for transactions and their child nodes efficiently.
## Project Structure
- src/main/java/org/example/service/TransactionService.java: Service class for handling transaction operations.
- src/main/java/org/example/repository/TransactionRepository.java: Repository interface for CRUD operations on transactions.
- src/main/java/org/example/entity/Transaction.java: Entity class representing a transaction.
- src/main/resources/application.properties: Configuration file for the application.


## Approaches for Sum Calculation
### Recursive Approach
#### Description: The recursive approach involves traversing the entire tree of transactions each time the sum is calculated. This method can be simple to implement but may lead to performance issues with large datasets as it requires multiple database queries and deep recursion.
 - Implementation: The getSum method calculates the sum by recursively traversing the child nodes. This approach can be useful for small to medium-sized datasets where recursion depth is manageable.
 - Time Complexity: O(n) - where n is the number of transactions in the tree. The complexity arises from the need to visit each node to compute the sum.
 - Not Suitable For: Large datasets or scenarios with deep recursion due to potential stack overflow and performance degradation.
 - Update/Create Complexity: O(1) - Direct updates are fast as they don’t require traversing the entire tree.

##### Pros:
 - Simple and straightforward implementation.
 - Useful for datasets where tree size and depth are controlled.
##### Cons:
 - Performance can degrade with increasing dataset size.
 - Potential risk of stack overflow with very deep recursion.
 - Multiple database queries are required for each sum calculation, which can be inefficient.

### Efficient Approach
#### Description: The efficient approach involves maintaining aggregate values within the database to avoid recalculating sums repeatedly. This method ensures that the total sum is updated in real-time as transactions are added or modified.

- Implementation: The getSumEfficientApproach method retrieves the precomputed sum from the database, which has been updated during transaction creation or modification. This approach is more suitable for larger datasets and frequent sum calculations.
##### Pros:
 - Performance: Provides constant-time retrieval (O(1)) for sum calculations by leveraging precomputed values.
 - Scalability: More scalable for larger datasets, as it avoids deep recursion and excessive database queries.
##### Cons:
 - Complexity: Requires additional logic to keep aggregate values up-to-date, which may complicate the implementation.
 - Database Overhead: Updating aggregate values on every transaction create or update can lead to increased write operations, especially in a highly transactional system.
##### Use Cases:
 - Ideal for applications with large datasets or where performance and quick sum retrieval are critical.
 - Suitable for systems where transactions are frequent and need real-time aggregation without performance bottlenecks.

## Additional Considerations for Both Approaches
- Handling Cyclic References: In the recursive approach, special care must be taken to detect and handle cycles in the transaction tree to prevent infinite loops and stack overflow errors.
- Consistency: Ensure that the efficient approach maintains data consistency, especially during concurrent transactions or when updates are made to parent nodes.
- Testing and Validation: Thorough testing is necessary for both approaches to validate correctness, performance, and to identify potential edge cases or bottlenecks.
- If we consider a cycle is possible , then we can maintain a sepaarte columns where we store all its child nodes and perform calculations while creating and updating the db.
- ## When cycles are possible, maintain a separate column for child nodes and precomputed aggregate sums to facilitate real-time updates and calculations, and implement cycle detection to manage potential infinite loops.
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
