# Poseidon Capital Solutions Trading Platform

## 🚀 Project Description

Poseidon Capital Solutions Trading Platform is a robust web application for trading management, offering a comprehensive solution for managing transactions, curve points, ratings, and users.

## Documentation

All project documentation is available in the `docs` directory of the GitHub repository and is deployed online:

- [**JaCoCo Reports**](https://fraigneau.github.io/Fraigneau-Lucas-P7-java/jacoco/)
- [**Surfire Reports**](https://fraigneau.github.io/Fraigneau-Lucas-P7-java/surefire/)
- [**JavaDoc**](https://fraigneau.github.io/Fraigneau-Lucas-P7-java/javadoc/)

## 🛠 Technologies Used

- **Backend**:
  - Java 21
  - Spring Boot 3.4.3
  - Spring Security
  - Spring Data JPA
  - MapStruct
  - Lombok

- **Frontend**:
  - Thymeleaf
  - Bootstrap 4
  - HTML5

- **Database**:
  - MySQL

- **Testing Tools**:
  - JUnit 5
  - Mockito
  - Spring Test

- **API Documentation**:
  - SpringDoc OpenAPI (Swagger)

## ✨ Key Features

1. **User Management**
   - Authentication and authorization
   - User creation, modification, and deletion
   - Different roles (ADMIN, USER)

2. **Bid List Management**
   - Bid creation and tracking
   - Complete CRUD operations

3. **Curve Points**
   - Financial curve point management
   - Data tracking and analysis

4. **Ratings**
   - Management of Moody's, S&P, and Fitch ratings
   - Financial ranking tracking

5. **Transactions**
   - Transaction recording and tracking
   - Comprehensive transaction details

6. **Custom Rules**
   - Business rule creation and management
   - Flexibility for specific configurations

## 🔐 Security

- Spring Security-based authentication
- Role and permission management
- Password encoding
- Protection against unauthorized access

## 📦 Project Structure

```
src
├── main
│   ├── java
│   │   └── com/poseidoncapitalsolutions/trading
│   │       ├── config           # Spring Configuration
│   │       ├── controller       # Spring MVC Controllers
│   │       ├── dto              # Data Transfer Objects
│   │       ├── exception        # Custom Exception Handling
│   │       ├── mapper           # MapStruct Mappers
│   │       ├── model            # JPA Entities
│   │       ├── repository       # Spring Data Repositories
│   │       └── service          # Business Services
│   └── resources
│       ├── static               # Static Resources
│       ├── templates            # Thymeleaf Templates
│       └── application.properties
└── test                         # Unit and Integration Tests
```

## 🚀 Installation and Configuration

### Prerequisites
- Java 21
- Maven
- MySQL

### Installation Steps

1. Clone the repository
```bash
git clone https://github.com/fraigneau/Fraigneau_Lucas_P7_Java.git
cd Fraigneau_Lucas_P7_Java
```

2. Configure the Database
- Create a MySQL database
- Update `application.properties` with your credentials

3. Compile and Run the Application
```bash
mvn clean install
mvn spring-boot:run
```

## 🧪 Testing

- Unit testing with JUnit and Mockito
- Test coverage managed by JaCoCo
- Run tests:
```bash
mvn test
```

## 📊 API Documentation

- Swagger UI accessible at: `/swagger`
- OpenAPI documentation at: `/api-docs`

## 🔒 Default Credentials

- **Admin**:
  - Username: admin
  - Password: 123123

- **Standard User**:
  - Username: user
  - Password: 123123