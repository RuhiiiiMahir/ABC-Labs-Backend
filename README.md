# Lab Appointment System Backend Server

Welcome to the Lab Appointment System backend server repository. This system is designed to manage appointments and generate reports for laboratory tests. This README provides an overview of the backend architecture, API documentation, code structure, and setup instructions.

## System Overview

The Lab Appointment System follows a client-server architecture. The backend is developed using Spring Boot, a popular Java framework for building web applications. It interacts with the frontend, which is built using HTML, CSS, and JavaScript. The system stores data in a MySQL database, with Hibernate serving as the ORM framework for database operations.

## Components and Their Interactions

### Backend Components

- **Spring Boot Application**: Acts as the server-side application framework, providing RESTful APIs for communication with the frontend.
- **Controllers**: Handle incoming HTTP requests and route them to the appropriate service classes.
- **Services**: Implement business logic, such as appointment scheduling and report generation.
- **Repository Layer**: Utilizes Spring Data JPA to interact with the MySQL database.
- **Exception Handling**: Custom exception classes handle errors and provide appropriate HTTP responses.

### Database Layer

- **MySQL Database**: Stores data related to users, appointments, and test results.
- **Hibernate ORM**: Maps Java objects to database tables and manages object-relational mapping.

## API Documentation

The Lab Appointment System exposes RESTful APIs for various functionalities. Below are some of the endpoints:

- `/api/auth/register`: Register a new user.
- `/api/auth/login`: Login an existing user and obtain a JSON Web Token (JWT).
- `/api/appointments`: Manage appointments, including scheduling, updating, and canceling.
- `/api/reports`: Generate reports, such as appointment history and test results.

## Code Structure

The codebase follows a structured layout for better organization and maintainability:

1. **Controller Layer**: Defines API endpoints and handles HTTP requests.
2. **Repository Layer**: Performs CRUD operations on the database.
3. **Service Layer**: Implements business logic and interacts with repositories.
4. **Exception Handling**: Custom exceptions handle errors and provide meaningful responses.

## Setup and Installation

To set up the backend server locally, follow these steps:

1. Clone this repository to your local machine.
2. Configure the application properties to connect to your MySQL database.
3. Build and run the application using Maven or your preferred IDE.

## Contributing

Contributions to the Lab Appointment System are welcome! Feel free to submit bug reports, feature requests, or pull requests to help improve the system.

## License

This project is licensed under the [MIT License](LICENSE).
