<div align="center">

# 🌐 EventSphere 🌟

<p align="center">
  <img src="https://img.shields.io/badge/Java-22-ED8B00?style=for-the-badge&logo=java&logoColor=white" alt="Java 22" />
  <img src="https://img.shields.io/badge/Spring_Boot-4.0.5-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL" />
  <img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white" alt="Redis" />
  <img src="https://img.shields.io/badge/Gemini_API-8E75B2?style=for-the-badge&logo=google&logoColor=white" alt="Gemini API" />
</p>

**A robust, AI-powered virtual event management platform built with modern Java & Spring Boot.**

[Explore Features](#sparkles-features) • [Installation](#rocket-getting-started) • [API Docs](#books-api-documentation) • [Contribute](#handshake-contributing)

</div>

---

## 📖 Overview

**EventSphere** provides a comprehensive suite of tools for creating, managing, and participating in virtual events. It elevates the standard event experience by offering **real-time chat**, **AI-powered matchmaking** via the Gemini API, and a seamless, secure authentication flow using **JWT & GitHub OAuth2**. 

Whether you're hosting a small meetup or a large tech conference, EventSphere's scalable architecture ensures a rich and interactive user experience. ✨

---

## :sparkles: Features

*   📅 **Event Management**: Seamlessly create, update, and manage your events with ease.
*   🔐 **User Authentication**: Rock-solid security featuring user registration, **JWT-based login**, and **GitHub OAuth2** integration.
*   💬 **Real-time Chat**: Keep the conversation flowing with instant, real-time messaging for participants via **WebSockets**.
*   🤖 **AI Matchmaking**: Intelligent networking! Get personalized matchmaking and connection suggestions powered by the **Google Gemini API**.
*   📚 **Interactive API Documentation**: Instantly explore and test all API endpoints visually using **OpenAPI (Swagger UI)**.
*   🚀 **Scalable Architecture**: Engineered with modern best practices, utilizing **Spring Data JDBC**, **Redis** caching, and **MySQL**.

---

## 🛠️ Technologies Used

### Core Frameworks & Languages
*   **Java 22**
*   **Spring Boot 4.0.5** (Core Framework)

### Data & Caching
*   **Spring Data JDBC** (Database interaction)
*   **MySQL** (Relational Database)
*   **Redis** (In-memory data store for caching and sessions)
*   **Flyway** (Reliable database migration management)

### Security, Web & APIs
*   **Spring Security & OAuth2 Client** (Authentication & Authorization)
*   **Spring Web** (REST API creation)
*   **Spring WebSocket** (Real-time communication)
*   **JWT** (JSON Web Tokens for secure auth)
*   **Gemini API** (AI-powered intelligence features)

### Utilities
*   **Lombok** (Boilerplate reduction)
*   **OpenAPI (SpringDoc)** (API Documentation & UI)

---

## 🗂️ Project Structure

```text
eventsphere/
├── src/
│   ├── main/
│   │   ├── java/dev/pasinduog/eventsphere/
│   │   │   ├── config/         ⚙️ Spring configuration files
│   │   │   ├── controller/     🌐 REST API controllers
│   │   │   ├── dto/            📦 Data Transfer Objects
│   │   │   ├── exception/      ⚠️ Custom exception handlers
│   │   │   ├── filter/         🛡️ Request filters (JWT, etc.)
│   │   │   ├── model/          🏗️ Entities
│   │   │   ├── repository/     🗄️ Data access layer
│   │   │   ├── service/        💼 Business logic & integrations
│   │   │   └── Application.java▶️ Main application entry point
│   │   └── resources/
│   │       ├── db/migration/   📜 Database migration scripts (Flyway)
│   │       └── application.yml 🛠️ Application configuration
├── pom.xml                     🐘 Maven project configuration
└── README.md                   📄 You are here!
```

---

## :rocket: Getting Started

Ready to run EventSphere locally? Follow these steps to get your environment set up.

### Prerequisites

Ensure you have the following installed:
*   ☕ **JDK 22**
*   🐘 **Maven**
*   🐬 **MySQL**
*   🔴 **Redis**

### Installation

1.  **Clone the repository**
    ```bash
    git clone https://github.com/your_username_/eventsphere.git
    cd eventsphere
    ```

2.  **Set up the Database**
    Ensure MySQL is running and create the database (Flyway will automatically handle table creation on startup).
    ```sql
    CREATE DATABASE IF NOT EXISTS virtual_events_db;
    ```

3.  **Set up Environment Variables**
    The application relies on several environment variables. Set them in your system environment or IDE run configuration:
    
    | Variable | Description |
    | :--- | :--- |
    | `DB_USERNAME` | Your MySQL username |
    | `DB_PASSWORD` | Your MySQL password |
    | `GITHUB_CLIENT_ID` | Your GitHub OAuth app Client ID |
    | `GITHUB_CLIENT_SECRET` | Your GitHub OAuth app Client Secret |
    | `GEMINI_API_KEY` | Your Google Gemini API Key |
    | `JWT_KEY` | A secure, random string for JWT signing |
    | `BASE_URL` | The frontend base URL for CORS/redirects |

4.  **Install dependencies & Run**
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

---

## :books: API Documentation

Once the application is running, you can access the gorgeous, interactive API documentation!

👉 **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

---

## :handshake: Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**! ❤️

1.  **Fork** the Project
2.  Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3.  Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4.  Push to the Branch (`git push origin feature/AmazingFeature`)
5.  Open a **Pull Request**

<div align="center">
  <br>
  <i>Built with ❤️ by the EventSphere team.</i>
</div>