### 📦 Project Overview
This project is a basic chat system built using a monolithic architecture.
The goal is to provide a modular and extensible chat core service that can be
easily integrated into other business scenarios, such as:  
Customer service systems  
Social networking platforms  
Real-time messaging services  
At the same time, the project maintains manageable complexity to reduce maintenance and learning costs.

### 🛠️ Project Environment

![env](/image/coobaIM.jpg)

### 🚀 Quick Start
1. Launch all services using the following command:  
(Note: The first run may take a while as it downloads all required dependencies)

``` bash
docker compose up -d
```
2. Then, start the main project using IntelliJ IDEA.

### 🧩 Project Layered Architecture
This project adopts a clear layered design and strictly avoids cross-layer calls, improving modular maintainability and readability:

1. Controller Layer 
Responsibility: Entry point for external requests
   - Exposes RESTful APIs 
   - Handles request validation and parameter transformation 
   - Returns responses in a unified format 
   - Integrates Swagger/OpenAPI for auto-generated API documentation

2. Component Layer  
Responsibility: Business logic orchestration 
   - Acts as a mediator for Services, encapsulating complex business logic 
   - Decouples direct dependencies between Services 
   - Enables parallel or orchestrated execution of multiple Service logics

3. Service Layer  
Responsibility: Implementation of business operations
   - Focuses on individual business units (e.g., user management, message handling)
   - Groups logic and data structures by domain (e.g., user_, chat_)
   - Each service class is testable and supports unit testing for CI

4. Repository Layer  
Responsibility: Data access and cache abstraction
   - Encapsulates data access logic and hides low-level SQL implementations
   - Supports complex queries (JOINs, pagination, etc.)
   - Integrates caching strategies (e.g., Redis) to reduce hot data query load

5. SQL Layer (Mapper)  
Responsibility: Centralized SQL management and optimization
   - Centralizes all SQL definitions and management
   - Supports dynamic SQL generation
   - Acts as the lowest layer interacting with the database, making future SQL changes easier

### 🧱 Additional Module Design
The user connection logic (via STOMP) is separated from the core module.
The core module is designed to be replaceable, allowing technologies like native WebSocket or MQTT to be used instead.

### 💻  Local Develop
To run the project locally, first clone the frontend repository into the web folder:  
👉 https://github.com/linchuen/CoobaIM-app.git  
Next, execute the SQL scripts located in the sql folder to initialize the database tables.   

This project uses STOMP for server communication, with a message queue (MQ) enabled by default.  
Both Kafka and ActiveMQ Artemis are supported. If you do want to run in a single-server setup,
you can disable them in application.yml by setting:
```yaml
stomp:
  artemis:
    enabled: false
  kafka:
    enabled: false
```
⚠️ When using Kafka, make sure each server instance has a unique group ID to avoid conflicts.

