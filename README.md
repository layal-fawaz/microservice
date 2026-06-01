# AI-Powered Project Planning Platform

**Student:** Layal Fawaz Alhusseini  
**ID:** 220221025  
**Course:** Advanced Software Engineering  
**Instructor:** Dr. Abdelkareem Alashqar  

---

## System Description

An intelligent platform that transforms unstructured project ideas into clear and actionable execution plans. Users input their project idea and add team members with their skills. The system analyzes the data, assigns roles, distributes tasks, and generates a project plan with a timeline.

---

## Microservices Overview

| Service | Port | Description |
|---------|------|-------------|
| user-service | 9001 | User registration, login, token validation |
| planning-service | 9002 | Project plans and roadmap generation |
| team-service | 9003 | Team members and skills management |
| idea-service | 9004 | Idea submission and AI analysis trigger |
| task-service | 9005 | Task creation, assignment, and status updates |
| progress-service | 9006 | Progress tracking and project summary |
| payment-service | 9007 | Subscription validation |

---

## Architecture

Each microservice is a fully independent Spring Boot application with:
- Its own H2 in-memory database
- Its own REST API
- Its own Docker container

### Communication Styles

| From | To | Style | Technology |
|------|----|-------|------------|
| Idea Service | Payment Service | Synchronous REST | RestTemplate |
| Idea Service | Planning Service | Asynchronous Non-blocking | RabbitMQ |
| Planning Service | Team Service | Synchronous REST | RestTemplate |
| Task Service | Progress Service |  Event-Driven (Kafka - designed, simulated with REST) | RestTemplate |
| User Service | Frontend/Others | Synchronous REST | RestTemplate |

---

## Technologies Used

- **Spring Boot** — microservice framework
- **Spring Web** — REST API endpoints
- **Spring Data JPA** — database operations
- **H2 Database** — lightweight in-memory database
- **Lombok** — reduces boilerplate code
- **Spring AMQP** — RabbitMQ integration (idea and planning services)
- **Docker** — containerization
- **GitHub Actions** — CI/CD pipeline
- **Docker Hub** — Docker image registry

---

## How to Run

### Prerequisites
- Java 17
- Docker Desktop

### Step 1 — Start RabbitMQ
```bash
docker run -d -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

### Step 2 — Start Services
Open a terminal for each service in this order:
```bash
cd payment-service   && ./gradlew bootRun
cd user-service      && ./gradlew bootRun
cd team-service      && ./gradlew bootRun
cd planning-service  && ./gradlew bootRun
cd progress-service  && ./gradlew bootRun
cd task-service      && ./gradlew bootRun
cd idea-service      && ./gradlew bootRun
```

### Step 3 — Run with Docker (team-service)
```bash
cd team-service
docker build -t team-service .
docker run -p 8081:9003 team-service
```

---

## API Endpoints

### Payment Service — :9007
```
POST /api/v1/payment/subscribe
GET  /api/v1/payment/validate/{userId}
```

### User Service — :9001
```
POST /api/v1/auth/register
POST /api/v1/auth/login
GET  /api/v1/auth/validate?token=
```

### Team Service — :9003
```
POST /api/v1/team
GET  /api/v1/team
GET  /api/v1/team/{id}
```

### Planning Service — :9002
```
POST /api/v1/plans
GET  /api/v1/plans
GET  /api/v1/plans/full-details/{id}
```

### Idea Service — :9004
```
POST /api/v1/ideas
GET  /api/v1/ideas
POST /api/v1/ideas/{id}/analyze
```

### Task Service — :9005
```
POST /api/v1/tasks
PUT  /api/v1/tasks/{id}/assign
PUT  /api/v1/tasks/{id}/status
```

### Progress Service — :9006
```
GET  /api/v1/progress
GET  /api/v1/progress/project/{id}
GET  /api/v1/progress/project/{id}/summary
```

---

## Test Flow (Postman)

1. Subscribe user → `POST :9007/api/v1/payment/subscribe`
2. Add team member → `POST :9003/api/v1/team`
3. Submit idea → `POST :9004/api/v1/ideas`
4. Analyze idea → `POST :9004/api/v1/ideas/1/analyze`
   - Validates payment (sync REST)
   - Sends to RabbitMQ (async non-blocking)
5. Verify plan created → `GET :9002/api/v1/plans`
6. Get full details → `GET :9002/api/v1/plans/full-details/1`
   - Returns plan + team member (sync REST)
7. Create task → `POST :9005/api/v1/tasks`
8. Update status → `PUT :9005/api/v1/tasks/1/status`
9. Verify progress → `GET :9006/api/v1/progress`

---

## CI/CD Pipeline

Applied on **planning-service** and **team-service** using GitHub Actions and Docker Hub.

Every push to `main` branch automatically:
1. Checks out the code
2. Sets up Java 17
3. Builds the JAR using Gradle
4. Logs in to Docker Hub
5. Builds and pushes the Docker image

Docker Hub: [layal12/planning_service](https://hub.docker.com/r/layal12/planning_service) | [layal12/team_service](https://hub.docker.com/r/layal12/team_service)

---

## Postman Collection

Import `AI_Project_Planning.postman_collection.json` to test all endpoints.
