# Stage 1: Build
FROM maven:3.9.1-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml dan unduh dependensi
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code dan build jar
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy jar dari stage build
COPY --from=build /app/target/*.jar app.jar

# Expose port Spring Boot default
EXPOSE 3000

# Jalankan Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
