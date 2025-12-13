# -----------------------------
# Stage 1: Build the JAR
# -----------------------------
# Official Maven image with JDK to compile the application
FROM maven:3.9.8-eclipse-temurin-21 AS build

# Set working directory inside the container
WORKDIR /app

# Copy only Maven configuration first to leverage Docker cache
COPY pom.xml .
COPY src ./src

# Build the application and generate the JAR, skipping tests for faster build
RUN mvn clean package -DskipTests

# -----------------------------
# Stage 2: Final runtime image
# -----------------------------
# Lightweight image with only the JRE to run the application
FROM eclipse-temurin:21-jre-jammy

# Set working directory inside the container
WORKDIR /app

# Copy only the JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the application will run on (default Spring Boot port 8080)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
