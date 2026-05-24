## Microservices Communication Implementation 

## Project Overview
This is a Java-based microservice architecture developed using **Spring Boot**. The system demonstrates how two independent services can communicate and aggregate data using the **API Composition** pattern.

## Architecture & Communication
The system consists of two main services that interact synchronously:

1. **Planning Service (Port 9002):**
   - Manages project plan data.
   - Uses `RestTemplate` to fetch external data from the Team Service.
   - Aggregates local data and remote data into a `ResponseVO`.

2. **Team Service (Port 9003):**
   - Manages team member details.
   - Provides an endpoint to retrieve member metadata by ID.

## Core Implementation Logic
The integration is handled in the `ProjectPlanService` class. When a request is made for plan details, the following steps occur:
* The service retrieves the `ProjectPlan` from the repository.
* A GET request is sent to `http://localhost:9003/api/v1/team/{id}`.
* The response is mapped to a `TeamMemberVO` object.
* Both objects are combined and returned as a single JSON response.

## Technologies Used
* **Java** (Core Language)
* **Spring Boot** (Framework)
* **Spring Data JPA** (Database Access)
* **RestTemplate** (Inter-service Communication)

## Endpoints
* **Aggregated Result:** `GET http://localhost:9002/api/v1/plans/full-details/{id}`
* **Team Resource:** `GET http://localhost:9003/api/v1/team/{id}`

---
*Advanced Software Engineering Project*
