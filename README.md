# Expense Tracker Backend - Spring Boot

This is the backend API for the Daily Expense Tracker application, built with **Spring Boot 3.2**, **Spring Data JPA**, **MySQL**, and **Maven**.

## Project Structure

```
backend/
├── src/main/java/com/expensetracker/
│   ├── ExpenseTrackerApplication.java    ← Main entry point
│   ├── config/
│   │   ├── CorsConfig.java              ← CORS configuration
│   │   └── GlobalExceptionHandler.java  ← Global error handling
│   ├── controller/
│   │   └── ExpenseController.java       ← REST endpoints
│   ├── dto/
│   │   └── ExpenseDTO.java              ← Request/Response DTOs
│   ├── entity/
│   │   ├── User.java                    ← User entity
│   │   └── Expense.java                 ← Expense entity
│   ├── repository/
│   │   ├── UserRepository.java          ← User data access
│   │   └── ExpenseRepository.java       ← Expense data access
│   ├── service/
│   │   └── ExpenseService.java          ← Business logic
│   └── util/
│       └── Logger.java                  ← Logging utility
├── src/main/resources/
│   └── application.properties           ← Configuration
├── pom.xml                              ← Maven dependencies
└── README.md
```

## Prerequisites

- **Java 17+** (JDK)
- **Maven 3.6+**
- **MySQL 8.0+**

## Installation

### 1. Create MySQL Database

```sql
CREATE DATABASE expense_tracker;
USE expense_tracker;
```

### 2. Build the Project

```bash
cd backend
mvn clean install
```

This command downloads all dependencies and compiles the project.

### 3. Configure Database Connection

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/expense_tracker?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=your_password
```

## Running the Application

### Option 1: Using Maven

```bash
cd backend
mvn spring-boot:run
```

### Option 2: Using Java Command

```bash
cd backend
mvn clean package
java -jar target/expense-tracker-backend-1.0.0.jar
```

The server will start on **http://localhost:8080**

### Verify Server is Running

```bash
curl http://localhost:8080/api/expenses?year=2025&month=1
```

## API Endpoints

All endpoints are prefixed with `/api` (configured in `application.properties`).

### Create Expense
- **Endpoint:** `POST /api/expenses`
- **Request Body:**
  ```json
  {
    "date": "2025-01-15",
    "amount": 50.00,
    "category": "Food",
    "description": "Lunch at restaurant"
  }
  ```
- **Response:** `201 Created`
  ```json
  {
    "id": 1,
    "date": "2025-01-15",
    "amount": 50.00,
    "category": "Food",
    "description": "Lunch at restaurant",
    "createdAt": "2025-01-17T10:30:00",
    "updatedAt": "2025-01-17T10:30:00"
  }
  ```

### Update Expense
- **Endpoint:** `PUT /api/expenses/{id}`
- **Request Body:** (all fields optional)
  ```json
  {
    "amount": 55.00,
    "category": "Food & Dining"
  }
  ```
- **Response:** `200 OK`

### Delete Expense
- **Endpoint:** `DELETE /api/expenses/{id}`
- **Response:** `204 No Content`

### List Expenses by Month
- **Endpoint:** `GET /api/expenses?year=2025&month=1`
- **Response:** `200 OK`
  ```json
  {
    "expenses": [
      {
        "id": 1,
        "date": "2025-01-15",
        "amount": 50.00,
        "category": "Food",
        "description": "Lunch",
        "createdAt": "2025-01-17T10:30:00",
        "updatedAt": "2025-01-17T10:30:00"
      }
    ],
    "total": 50.00,
    "count": 1,
    "year": 2025,
    "month": 1
  }
  ```

### Batch Create Expenses
- **Endpoint:** `POST /api/expenses/batch`
- **Request Body:**
  ```json
  {
    "expenses": [
      {
        "date": "2025-01-15",
        "amount": 50.00,
        "category": "Food",
        "description": "Lunch"
      },
      {
        "date": "2025-01-16",
        "amount": 30.00,
        "category": "Transport",
        "description": "Taxi"
      }
    ]
  }
  ```
- **Response:** `201 Created`
  ```json
  {
    "created": [
      { /* expense 1 */ },
      { /* expense 2 */ }
    ],
    "failed": null,
    "totalCreated": 2
  }
  ```

## Database Schema

### Users Table
```sql
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    open_id VARCHAR(64) UNIQUE NOT NULL,
    name VARCHAR(255),
    email VARCHAR(320),
    login_method VARCHAR(64),
    role ENUM('USER', 'ADMIN') DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_signed_in TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Expenses Table
```sql
CREATE TABLE expenses (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    date DATE NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    category VARCHAR(50) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_date (user_id, date),
    INDEX idx_date (date)
);
```

## Logging

The application uses **SLF4J** with **Logback** for logging. Logs are output to the console with the following format:

```
[2025-01-17 10:30:00] - com.expensetracker.service.ExpenseService - Creating expense
```

Log levels are configured in `application.properties`:
- `logging.level.root=INFO` - Global log level
- `logging.level.com.expensetracker=DEBUG` - Application-specific debug logging

## Environment Variables

You can override application properties using environment variables:

```bash
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/expense_tracker
export SPRING_DATASOURCE_USERNAME=root
export SPRING_DATASOURCE_PASSWORD=your_password
mvn spring-boot:run
```

## Development

### Running Tests

```bash
mvn test
```

### Code Formatting

```bash
mvn spotless:apply
```

### Building for Production

```bash
mvn clean package -DskipTests
```

This creates an executable JAR file at `target/expense-tracker-backend-1.0.0.jar`

## Troubleshooting

### Database Connection Issues

**Error:** `Communications link failure`

**Solution:** Ensure MySQL is running and accessible:
```bash
mysql -u root -p
```

### Port Already in Use

**Error:** `Address already in use: bind`

**Solution:** Change the port in `application.properties`:
```properties
server.port=8081
```

### Maven Build Failures

**Error:** `Could not find a version that satisfies the requirement`

**Solution:** Clear Maven cache and rebuild:
```bash
mvn clean install -U
```

## Next Steps

1. **Authentication:** Implement JWT or OAuth2 authentication
2. **Authorization:** Add role-based access control (RBAC)
3. **Testing:** Add unit and integration tests
4. **Documentation:** Generate API documentation with Swagger/OpenAPI
5. **Deployment:** Deploy to cloud platforms (AWS, Azure, GCP)

## License

MIT License - See LICENSE file for details
# Expense-Tracker-Vaadin-Backend
