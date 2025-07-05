# BCI Java Assessment - User Registration API

RESTful API for user registration developed as part of the BCI Java Specialist Integration assessment.

## Technologies Used

- Java 8+
- Spring Boot 2.7+
- Spring Security with JWT
- Spring Data JPA
- H2 Database (in-memory)
- Maven
- JUnit 5
- Swagger/OpenAPI 3

## Features

- User registration with email and password validation
- JWT token generation and authentication
- Phone number management
- Comprehensive error handling
- Email format validation (must end with .cl)
- Configurable password validation
- H2 in-memory database
- Unit and integration tests
- API documentation with Swagger

## Getting Started

### Prerequisites

- Java 8 or higher
- Maven 3.6 or higher

### Installation

1. Clone the repository:
```bash
git clone https://github.com/YOUR_USERNAME/bci.git
cd bci

2. Build the project:

mvn clean compile

3. Run the tests:

mvn test

4. Start the application:

mvn spring-boot:run

The application will start on http://localhost:8080