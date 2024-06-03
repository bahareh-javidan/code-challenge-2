# Apromore Challenge Project

## Overview
This project is a Spring Boot application that provides REST APIs for managing students and courses. It includes functionalities to create, update, delete, and retrieve students and courses, as well as enroll students in courses.

## Table of Contents
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Testing](#testing)

## Technologies Used
- Java 17
- SpringBoot
- H2 Database
- Swagger/OpenAPI

## Getting Started

### Prerequisites
- Java 17
- Maven 3.6+

### Running the Application
First run `mvn clean install` to make the project ready for a run

Then run `mvn spring-boot:run`

The application will run and the APIs are ready to use: http://localhost:8080/api

## API Documentation
This project documented with OpenAPI and when you run the app you can go to http://localhost:8080/swagger-ui/index.html
to access the endpoints and all the required information.

## Testing
As the project is using H2 in-memory database, all inserted data in the application will be lost.
So you can start with adding student and courses in the beginning, following with enroll the students with the courses.
