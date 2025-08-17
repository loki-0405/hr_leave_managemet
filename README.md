# 🧑‍💼 HR Leave Management System (Spring Boot)

This is a backend API service for managing employee data, leave requests, approvals, HR actions, and authentication using Spring Boot and Spring Security (JWT).

---

## 🚀 Features
===============


- JWT-based Authentication (`/login`)
- Role-based Access Control (Admin/HR/Employee)
- Employee Dashboard & Leave Request Management
- HR Dashboard for Leave Approvals and Employee Management
- Secure password encryption using BCrypt
- RESTful APIs for full CRUD operations
- Custom DTOs for efficient data exchange

---

## 📬 REST API Endpoints

### 🔐 Authentication
===========================

| Method | Endpoint               | Description                     |
|--------|------------------------|---------------------------------|
| POST   | `/api/auth/login`      | Authenticate user and return JWT |
| POST   | `/api/auth/addemp`     | Add new employee (with managerId from auth user) |
| GET    | `/api/auth/test`       | Test endpoint to check auth     |
| GET    | `/api/auth/debug/{email}` | Debug user info by email      |

---

### 👨‍💼 Employee APIs (`/api/employee`)
=========================================

| Method | Endpoint                            | Description                           |
|--------|-------------------------------------|---------------------------------------|
| GET    | `/dashboard`                        | Employee dashboard                    |
| POST   | `/leaverequest`                     | Submit a leave request                |
| PATCH  | `/updateleaverequest/{id}`          | Update a leave request                |
| DELETE | `/deletelr/{id}`                    | Delete a leave request                |

---

### 🧑‍💼 HR APIs (`/api/hr`)
===========================


| Method | Endpoint                            | Description                                |
|--------|-------------------------------------|--------------------------------------------|
| GET    | `/dashboard`                        | Test HR dashboard                          |
| GET    | `/hrdashboard`                      | Get HR dashboard data                      |
| POST   | `/addemp`                           | Add new employee                           |
| PUT    | `/updateemp/{id}`                   | Full update of employee                    |
| PATCH  | `/updateemp/{id}`                   | Partial update of employee                 |
| DELETE | `/deleteemp/{id}`                   | Delete an employee                         |
| GET    | `/employee/{id}`                    | Get employee by ID                         |
| GET    | `/employees`                        | Get all employees                          |
| POST   | `/addleavedetails/{id}`            | Add leave entitlement for employee         |
| PATCH  | `/updleavedetails/{id}`            | Update leave entitlement                   |
| DELETE | `/delleavedetails/{id}`            | Delete leave entitlement                   |
| PATCH  | `/approve/{id}`                     | Approve a leave request                    |
| PATCH  | `/reject/{id}`                      | Reject a leave request                     |

---

## ⚙️ Installation
====================

### 📌 Prerequisites
=======================

- Java 17 or above
- Maven
- MySQL / PostgreSQL (or any supported RDBMS)
- IDE (IntelliJ IDEA, Eclipse, VS Code)

---

### 📁 Clone the Repository
=============================

bash
git clone https://github.com/yourusername/hr-leave-management.git
cd hr-leave-management



🔧 Configure application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/hr_db
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update

spring.security.jwt.secret=your_jwt_secret
spring.security.jwt.expiration=86400000

📦 Build & Run
================
# Build
mvn clean install

# Run
mvn spring-boot:run


Application runs on: http://localhost:8080


Dependencies
=============

<!-- Spring Boot Starters -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>

<!-- JWT (JSON Web Token) -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId> <!-- Use jjwt-gson if preferred -->
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>

<!-- Lombok for cleaner code with annotations -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>

<!-- MySQL Driver -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- Spring Boot DevTools (for development hot reload) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>

<!-- Spring Boot Testing -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<!-- Spring Security Testing -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>
