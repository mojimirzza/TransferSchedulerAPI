# Transfer Scheduler API 🚀

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.0-green.svg)
![Java](https://img.shields.io/badge/Java-21-blue.svg)
![Swagger](https://img.shields.io/badge/Swagger-3.0.0-blue.svg)
![Docker](https://img.shields.io/badge/Docker-20.10.8-blue.svg)
![H2 Database](https://img.shields.io/badge/H2-Database-lightgrey.svg)
![JWT](https://img.shields.io/badge/JWT-Auth-orange.svg)
![CI/CD](https://img.shields.io/badge/CI/CD-GitHub%20Actions-yellowgreen.svg)

Welcome to the **Transfer Scheduler API**! This is a Spring Boot application designed to schedule money transfers between accounts. The API is fully documented using Swagger and includes features like JWT authentication, environment-specific configurations, and CI/CD integration.

---

## 🌟 Features

- **Scheduled Money Transfers**: Automate money transfers between accounts with ease.
- **RESTful API**: Fully documented with Swagger for easy integration.
- **JWT Authentication**: Secure your API endpoints with JSON Web Tokens.
- **H2 Database**: Lightweight, in-memory database for development and testing.
- **Docker & Docker Compose**: Easily containerize and deploy the application.
- **CI/CD Pipeline**: Automated testing and deployment using GitHub Actions.
- **Seeding Process**: Pre-populate your database with initial data for testing.
- **System Design**: Well-structured and scalable architecture.
- **Testing Improvements**: Continuous improvements in unit and integration tests.

---

## 🛠️ Branches

The repository contains the following branches:

- **`feature/env-docker-ci`**: Environment setup with Docker and CI/CD integration.
- **`feature/jwt-auth`**: JWT-based authentication for secure API access.
- **`feature/seeding-process`**: Database seeding for initial data setup.
- **`feature/staging-environment`**: Configuration for the staging environment.
- **`feature/system-design`**: Architectural design and system structure.
- **`feature/testing-improvements`**: Enhancements in testing strategies.
- **`master`**: The main branch with the latest stable release.

---

## 🚀 Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6.x or higher
- Docker and Docker Compose (optional)
- H2 Database (in-memory)

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/mojimirzza/TransferSchedulerAPI.git
   cd TransferSchedulerAPI
Build the application:

bash
Copy
mvn clean install
Run the application:

bash
Copy
mvn spring-boot:run
Access Swagger UI:
Open your browser and navigate to http://localhost:8080/swagger-ui.html to explore the API documentation.

Docker Setup
Build and Run with Docker Compose:

bash
Copy
docker-compose up --build
Access the Application:
The API will be available at http://localhost:8080.

📄 API Documentation
The API is documented using Swagger. You can access the Swagger UI at:

Copy
http://localhost:8080/swagger-ui.html
🔒 Security
This API uses JWT (JSON Web Tokens) for authentication. Ensure that you secure your tokens and never expose them publicly.

🧪 Testing
The application includes a suite of unit and integration tests. To run the tests, use the following command:

bash
Copy
mvn test
🤝 Contributing
Contributions are welcome! Please follow these steps:

Fork the repository.

Create a new branch (git checkout -b feature/YourFeatureName).

Commit your changes (git commit -m 'Add some feature').

Push to the branch (git push origin feature/YourFeatureName).

Open a pull request.

📜 License
This project is licensed under the MIT License. See the LICENSE file for details.

📧 Contact
For any questions or suggestions, feel free to reach out:

Email: mojimirzza@example.com

GitHub: mojimirzza

🙏 Acknowledgments
Spring Boot: For providing a robust framework for building Java applications.

Swagger: For making API documentation easy and interactive.

Docker: For simplifying deployment and containerization.

