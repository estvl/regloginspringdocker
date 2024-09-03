# Use an official Maven image to build the application
FROM maven:3.8.4-openjdk-8 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and download the dependencies (these layers will be cached)
COPY pom.xml .

# Download all necessary dependencies
RUN mvn dependency:go-offline -B

# Copy the entire project source into the container
COPY src ./src

# Build the Spring Boot application
RUN mvn clean package -DskipTests

# Use an official OpenJDK runtime as a parent image
FROM openjdk:8-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build stage to the runtime stage
COPY --from=build /app/target/registration-login-demo-0.0.1-SNAPSHOT.jar /app/registration-login-demo.jar

# Expose the port on which the application will run
EXPOSE 8080

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "/app/registration-login-demo.jar"]
