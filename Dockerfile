# Use an official OpenJDK runtime as the base image
FROM amazoncorretto:19.0.2

# Set the working directory in the container
#WORKDIR /app

# Copy the Spring Boot application JAR file into the container
COPY target/*.jar app.jar

# Expose the port that the Spring Boot application will run on
#EXPOSE 8080

# Define environment variables (if needed)
#ENV SPRING_PROFILES_ACTIVE=dev

# Command to run the Spring Boot application when the container starts
CMD ["java", "-jar", "app.jar"]