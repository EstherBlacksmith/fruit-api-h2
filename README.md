## 🍎 Fruit API (H2)

> **A simple RESTful API for managing fruit data, built with Spring Boot and using an in-memory H2 database.**

[![Java](https://img.shields.io/badge/Language-Java-orange.svg)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Framework-Spring%20Boot-green.svg)](https://spring.io/projects/spring-boot)
[![H2 Database](https://img.shields.io/badge/Database-H2-blue.svg)](http://www.h2database.com/html/main.html)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

---

## Table of Contents

* [🌟 Features](#-features)
* [🛠️ Installation & Setup](#%EF%B8%8F-installation--setup)
* [🚀 Getting Started](#-getting-started)
* [📖 API Endpoints](#-api-endpoints)
* [⚙️ Database Access (H2 Console)](#%E2%99%90%EF%B8%8F-database-access-h2-console)
* [🤝 Contributing](#-contributing)
* [📝 License](#-license)

---

## 🌟 Features

This project provides a basic CRUD (Create, Read, Update, Delete) API for fruit entities.

* **RESTful Endpoints:** Standard HTTP methods (GET, POST, PUT, DELETE) for resource manipulation.
* **In-Memory Database:** Uses **H2 Database** for fast, simple setup and testing (data is wiped on restart).
* **Spring Data JPA:** Simplifies database interaction and persistence.
* **Automatic Seed Data:** The application automatically loads initial fruit data when it starts up.

---

## 🛠️ Installation & Setup

### Prerequisites

Ensure you have the following installed on your system:

* **Java Development Kit (JDK) 17+**
* **Apache Maven** (for building the project)
* **Git**

### Steps

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/EstherBlacksmith/fruit-api-h2.git](https://github.com/EstherBlacksmith/fruit-api-h2.git)
   cd fruit-api-h2
   ```

2. **Build the project:**
   Use Maven to compile the code and package it into a runnable JAR file.
   ```bash
   mvn clean install
   ```

---

## 🚀 Getting Started

### Running the API

You can run the application directly from the generated JAR file:

```bash
java -jar target/fruit-api-h2-0.0.1-SNAPSHOT.jar
````

The API will start on the default Spring Boot port, usually http://localhost:8080.

📖 API EndpointsThe base URL for all endpoints is http://localhost:8080/api/fruits.
| Method | Endpoint | Description | Request Body |Example|
|---|---|---|---|---|
| GET | /api/fruits|Retrieves a list of all fruits.|(None)|http://localhost:8080/fruits
| GET |/api/fruits/{id}|Retrieves a single fruit by its ID.|(None)|http://localhost:8080/fruits/1L
| POST|/api/fruits|Creates a new fruit entry.|"{""name"": ""Mango"", ""weightInKilos"":""
1""}"|http://localhost:8080/fruits?name=Mango&weightInKilos=1
| PUT |/api/fruits/{id}|Updates the fruit with the given ID.|"{""name"": ""Kiwi"", ""weightInKilos"": ""
1""}"|http://localhost:8080/fruits?ID=1l&name=Mango&weightInKilos=1
| DELETE |/api/fruits/{id}|Deletes the fruit with the given ID.|(None)|http://localhost:8080/fruits/1L

Example Request (using cURL)

To get all fruits after starting the server:

```
curl -X GET http://localhost:8080/api/fruits
```

⚙️ Database Access (H2 Console)

Since this project uses the H2 in-memory database, the Spring Boot configuration likely enables the H2 console for easy
inspection of the database schema and data.

1. Start the application (as shown in [🚀 Getting Started]).
2. Open your web browser and navigate to: http://localhost:8080/h2-console
3. Ensure the JDBC URL matches what is configured in application.properties (the default is often jdbc:h2:mem:testdb or
   similar).
4. Click Connect to view the database tables.

🤝 ContributingWe welcome contributions!

Please follow these general steps:

1. Fork the Project.
2. Create your Feature Branch (git checkout -b feature/NewEndpoint).
3. Commit your Changes (git commit -m 'feat: Add a new fruit endpoint').
4. Push to the Branch (git push origin feature/NewEndpoint).
5. Open a Pull Request.

📝 LicenseDistributed under the MIT License.


