## 🍎 Fruit & Provider Management API
 
This is a RESTful API built with **Spring Boot** designed to manage a fruit inventory along with its corresponding **Providers**. 

The application uses an **H2 in-memory database**, making it perfect for rapid development and testing.

[![Java](https://img.shields.io/badge/Language-Java-orange.svg)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Framework-Spring%20Boot-green.svg)](https://spring.io/projects/spring-boot)
[![H2 Database](https://img.shields.io/badge/Database-H2-blue.svg)](http://www.h2database.com/html/main.html)
[![Maven](https://badgen.net/badge/icon/maven?icon=maven&label)](https://https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## 📖 Table of Contents

* [🌟 Features](#-features)
* [🛠️ Installation & Setup](#%EF%B8%8F-installation--setup)
* [⚙️ Data Model](#%E2%99%90%EF%B8%8F-database-access-h2-console)
* [🚀 Getting Started](#-getting-started)
* [📖 API Endpoints](#-api-endpoints)
* [🤝 Contributing](#-contributing)
* [📝 License](#-license)

License
## 🚀 Features


-   **Fruit Management**: Full CRUD operations for fruit entities.

-   **Provider Tracking**: Manage suppliers and link them to specific fruit products.

-   **Relational Mapping**: Implements JPA associations between Fruits and Providers.

-   **H2 Console**: Built-in database interface for real-time data inspection.

-  **MySQL**: Persistence in MySQL container.

* * * * *
## 🛠️ Installation & Setup

### Prerequisites

Ensure you have the following installed on your system:

* **Java Development Kit (JDK) 17+**
* **Apache Maven** (for building the project)
* **Git**
* **Spring Boot 3.x**
* **Spring Data JPA**: For ORM and database interactions.
* **H2 Database**: Lightweight, in-memory SQL database.
* **Lombok**: To keep the code clean and concise.
* **MySQL**: Database.

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
* * * * *

## 📊 Data Model
-------------

### Fruit Entity

-   `id` (Long): Primary Key.

-   `name` (String): The name of the fruit.

-   `taste` (String): Flavor profile (e.g., Sweet, Acidic, Bitter).

-   `provider` (Provider): The associated supplier.

### Provider Entity

-   `id` (Long): Primary Key.

-   `name` (String): Supplier name.

-   `contact` (String): Contact information or address.

* * * * *

## 📑 API Endpoints
----------------

### 🍎 Fruits

| **Method** | **Endpoint** | **Description** |
| --- | --- | --- |
| **GET** | `/fruits` | Retrieve all fruits and filter by provider id. |
| **GET** | `/fruits/{id}` | Get fruit details by ID. |
| **POST** | `/fruits` | Register a new fruit (can include provider info). |
| **PUT** | `/fruits/{id}` | Update an existing fruit. |
| **DELETE** | `/fruits/{id}` | Remove a fruit from the record. |

### 🚚 Providers

| **Method** | **Endpoint** | **Description** |
| --- | --- | --- |
| **GET** | `/providers` | List all available providers. |
| **POST** | `/providers` | Create a new provider entry. |
| **DELETE** | `/providers/{id}` | Delete a provider. |

* * * * *

## 💻 Getting Started
------------------

1.  **Clone the repository:**

    Bash

    ```
    git clone https://github.com/EstherBlacksmith/fruit-api-h2.git

    ```

2.  **Run the application:**

    Bash

    ```
    mvn spring-boot:run

    ```

3.  Access the API:

    The server starts at http://localhost:8080

### Database Inspection (H2 Console)

You can view the tables and data by navigating to:

-   **URL:** `http://localhost:8080/h2-console`

-   **JDBC URL:** `jdbc:h2:mem:testdb`

-   **User:** `sa` | **Password:** *(leave empty)*

* * * * *

## 📝 Usage Example (POST Fruit)
-----------------------------

JSON

```
{
  "name": "Green Apple",
  "taste": "Sour",
  "provider": {
    "id": 1
  }
}
```
🤝 ContributingWe welcome contributions!

Please follow these general steps:

1. Fork the Project.
2. Create your Feature Branch (git checkout -b feature/NewEndpoint).
3. Commit your Changes (git commit -m 'feat: Add a new fruit endpoint').
4. Push to the Branch (git push origin feature/NewEndpoint).
5. Open a Pull Request.


## 📄 License
MIT License

Copyright (c) 2024 EstherBlacksmith

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
