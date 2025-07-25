# API Gateway for EduExcellence Microservices

This API Gateway serves as the single entry point for all microservices in the EduExcellence system.

## Configuration

- **Port**: 8765
- **Service Name**: api-gateway
- **Eureka Server**: http://localhost:8761

## Routing Rules

### Student Service Routes
- `GET/POST/PUT/DELETE /api/students/**` → Routes to `studentms` (port 8080)

### Fee Management Service Routes  
- `GET/POST/PUT/DELETE /api/fees/**` → Routes to `fee-management-ms` (port 8082)

### Health Check Routes
- `/health/students/**` → Student service health checks
- `/health/fees/**` → Fee management service health checks

### Eureka Server Access
- `/eureka/**` → Access to Eureka dashboard

## Example API Calls

Instead of calling services directly:
- ~~http://localhost:8080/students~~ 
- ~~http://localhost:8082/fees~~

Use the API Gateway:
- `http://localhost:8765/api/students`
- `http://localhost:8765/api/fees`

## Features

- **Load Balancing**: Automatic load balancing through Eureka service discovery
- **CORS Support**: Cross-origin requests enabled
- **Request Logging**: All requests are logged for monitoring
- **Circuit Breaker Ready**: Compatible with resilience4j patterns
- **Health Monitoring**: Actuator endpoints exposed

## Running the Gateway

```bash
cd apigateway
mvn spring-boot:run
```

The gateway will start on port 8765 and automatically register with Eureka server.
