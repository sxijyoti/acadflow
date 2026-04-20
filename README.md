# Acadflow
OOAD Mini Project - A comprehensive Academic Management System

## Overview
Acadflow is a robust Academic Management System combining a Spring Boot backend with a rich JavaFX frontend interface. It supports multiple hierarchical user roles (Students, Instructors, and Admins) to streamline academic operations including enrollments, assignments, exams, holidays, and timetables.

## Features

### Student
* **Dynamic Dashboard:** View enrolled subjects, pending assignments, and upcoming exams.
* **Unified Calendar:** Visual representation of assignments, exams, and holidays.
* **Assignments & Submissions:** Track pending work and submit assignments.
* **Profile Management:** View and manage personal details dynamically.

### Instructor (Teacher)
* **Resource Management:** Upload and share educational resources for subjects.
* **Assignment Creation:** Create and distribute coursework to students.
* **Profile Updates:** Keep instructor profiles up to date.

### Admin
* **Subject Management:** Create and manage distinct course subjects.
* **Timetable Scheduling:** Add and manage daily timetable slots.
* **Exam Scheduling:** Schedule and manage exams for various subjects.
* **Holiday Management:** Add and define academic and public holidays globally.

## Setup & Running the Application

### Running with Docker (Recommended)
This project uses Docker and Docker Compose to containerize the database and backend services, making setup effortless. 

1. Ensure Docker is installed and running on your system.
2. (Optional) Configure environment variables in a `.env` file for MySQL credentials.
3. Start the application stack (MySQL Database + Spring Boot Backend) by running:
   ```bash
   docker-compose up --build -d
   ```
   *The backend application will be available on `localhost:8080`, and the database on `localhost:3306`.*

To view the application logs:
```bash
docker-compose logs -f app
```

To shut down the application:
```bash
docker-compose down
```

### Running Locally (Without Docker)

Before running the application locally, set your MySQL database password environment variable:

```bash
export DB_PASSWORD=your_mysql_root_password
```

#### Running the JavaFX Client
Since this is a Spring Boot application with a rich JavaFX client, you can launch the graphical interface directly:

```bash
mvn clean javafx:run -DskipTests
```

*(Note: The JavaFX application implicitly bootstraps the Spring application context, running both the UI and the underlying services).*

#### Running the Standalone Backend (Optional)
If you only need to run the backend services without the UI natively:
```bash
mvn spring-boot:run -DskipTests
```
The application context will start on `localhost:8080`.
