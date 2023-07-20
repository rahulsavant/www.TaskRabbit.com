# TaskRabbit.com Project

TaskRabbit.com is an online platform that connects users seeking various services with skilled taskers available in their local area. This repository contains the source code and documentation for the TaskRabbit.com project. The project is built using Java, Spring Boot, and other technologies to provide a seamless and secure experience for users and taskers.

## Table of Contents
1. [Introduction](#introduction)
2. [Features](#features)
3. [Installation](#installation)
4. [Usage](#usage)
5. [API Endpoints](#api-endpoints)
6. [Authentication](#authentication)
7. [Database](#database)
8. [Email Integration](#email-integration)
9. [Payment Integration](#payment-integration)
10. [Testing](#testing)
11. [Deployment](#deployment)
12. [Contributing](#contributing)
13. [License](#license)

## Introduction
TaskRabbit.com is an online platform where users can find and book various services provided by skilled taskers. Taskers can create profiles, list their services, and receive bookings from users. The platform also supports secure authentication, online payment processing, and email notifications.

## Features
- Add, get, and delete services for taskers to list their offered services.
- Find services by name to help users easily discover relevant services.
- Add, get, and delete tasks for users to request specific services from taskers.
- Book taskers for tasks they want to complete.
- User authentication and authorization using Spring Security and JWT tokens.
- Password reset functionality via email.
- Integration with popular payment gateway for online payments.
- Email notifications for various events.

## Installation
1. Clone the repository:
   ```
   git clone https://github.com/your-username/TaskRabbit.git
   cd TaskRabbit
   ```
2. Install the required dependencies and packages.
   ```
   mvn install
   ```

## Usage
To run the application locally, use the following command:
```
mvn spring-boot:run
```

## API Endpoints
The following are the API endpoints available in the TaskRabbit.com application:

Add Task

Method: POST
Endpoint: /api/tasks
Request Body:json
Get Tasks for User

Method: GET
Endpoint: /api/users/{userId}/tasks
This endpoint retrieves all the tasks created by a specific user.
Delete Task

Method: DELETE
Endpoint: /api/tasks/{taskId}
This endpoint allows a user to delete their specific task.
Add Tasker

Method: POST
Endpoint: /api/taskers
Request Body:json
Get Taskers

Method: GET
Endpoint: /api/taskers
This endpoint retrieves a list of all registered taskers.
Delete Tasker

Method: DELETE
Endpoint: /api/taskers/{taskerId}
This endpoint allows a tasker to delete their profile from the platform.
Book Tasker for Task

Method: POST
Endpoint: /api/tasks/{taskId}/book
Request Body:json
This endpoint allows a user to book a specific tasker for their task and includes the agreed-upon price for the service.

## Authentication
TaskRabbit.com uses Spring Security and JWT token-based authentication. Users and taskers need to register and log in to access their respective features.

## Database
The application uses a MySQL database to store user and tasker information, services, tasks, and other relevant data. Refer to `database_schema.sql` for the database schema and setup instructions.

## Email Integration
TaskRabbit.com integrates with an email service to send notifications to users and taskers. Ensure you set up the appropriate email configuration in the `application.properties` file.

## Payment Integration
The application supports online payment processing by integrating with a popular payment gateway. To enable this functionality, you need to provide the required API keys in the configuration file.

## Testing
Unit tests and integration tests are included in the project. To run the tests, use the following command:
```
mvn test
```

## Deployment
For deployment, we recommend using Docker and Kubernetes. You can find the Dockerfile and Kubernetes deployment files in the `deployment/` directory.

## Contributing
Contributions to TaskRabbit.com are welcome! Please follow the guidelines in the `CONTRIBUTING.md` file.

## License
TaskRabbit.com is released under the MIT License. See the `LICENSE` file for more details.
