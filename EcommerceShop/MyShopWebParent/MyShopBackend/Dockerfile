# Use an official OpenJDK runtime as the base image for Spring Boot
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built Spring Boot JAR file from the target directory into the container
COPY target/MyShopBackend-0.0.1-SNAPSHOT.jar /app/myshop-backend.jar

# Expose the port on which the Spring Boot application will run
EXPOSE 8080

# Set environment variables for your application (replace with actual values)


# Run the Spring Boot application
CMD ["java", "-jar", "/app/myshop-backend.jar"]
