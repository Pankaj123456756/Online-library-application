# Multi-stage build for React + Spring Boot
FROM node:18-alpine AS frontend-build

WORKDIR /app/frontend
COPY frontend/package*.json ./
RUN npm install
COPY frontend/ ./
RUN npm run build

# Backend stage
FROM openjdk:17-jdk-slim

WORKDIR /app

# Install MySQL client for database setup
RUN apt-get update && apt-get install -y mysql-client && rm -rf /var/lib/apt/lists/*

# Copy backend files
COPY backend/pom.xml ./
RUN ./mvnw dependency:go-offline
COPY backend/src ./src

# Copy frontend build
COPY --from=frontend-build /app/frontend/build ./static

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 8080

# Start script
COPY docker-entrypoint.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/docker-entrypoint.sh

ENTRYPOINT ["docker-entrypoint.sh"]
CMD ["java", "-jar", "target/library-management-1.0.0.jar"]
