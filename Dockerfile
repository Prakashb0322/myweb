# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and project files into the container
COPY . .

# Build the JAR file
RUN ./mvnw clean package -DskipTests

# Rename and move the JAR file
RUN mv target/*.jar app.jar

# Expose port 8080 (or change based on your application)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
