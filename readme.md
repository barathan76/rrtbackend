# 🛠️ RRT E-Commerce Backend

A robust and scalable **Spring Boot backend** for the **RRT E-Commerce Flutter App**, featuring secure **JWT token-based authentication**, modular REST APIs, and MySQL integration.

> 🔗 **Frontend Repository:** [RRT E-Commerce Flutter App](https://github.com/barathan76/rrt_ecommerce_app.git)

---

## ✨ Features

- ✅ User Authentication (Login, Register, Forgot Password)
- 🔐 **JWT Token-Based Authentication** for secure access
- 🛍️ Product Management
- 🛒 Cart Service
- ❤️ Wishlist Service
- 📦 Order Service
- 📍 Address Management
- 🙍 Profile Management
- Optimized API responses for mobile (Flutter frontend)

---

## 🗃️ Technologies Used

| Technology        | Purpose                       |
|-------------------|-------------------------------|
| **Java 17**       | Core programming language     |
| **Spring Boot 3** | Framework                     |
| **Spring Security** | JWT-based authentication   |
| **MySQL**         | Relational database           |
| **JPA (Hibernate)** | ORM                         |
| **Maven**         | Dependency management         |
| **Lombok**        | Reduces boilerplate code      |

---

## ⚙️ Configuration

### 📦 Required Dependencies

- ✅ MySQL Server (Ensure it’s running)
- ✅ Java 17
- ✅ Maven

---

### 🛠️ MySQL Setup

1. Open your MySQL terminal or a GUI like MySQL Workbench.
2. Run the following command to create the required database:

```sql
CREATE DATABASE rrtdb;
