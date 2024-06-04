# Stage 1: Build the application
FROM gradle:7.5.1-jdk17 AS build

# Set the working directory inside the container
WORKDIR /home/gradle/project

# Copy the Gradle wrapper and build files
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle.kts build.gradle.kts
COPY settings.gradle.kts settings.gradle.kts

# Copy the application source code
COPY src src

RUN chmod +x gradlew

# Run the bootJar task to create the JAR file
RUN ./gradlew bootJar

# Stage 2: Create the runtime image
FROM eclipse-temurin:17-jre-jammy

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /home/gradle/project/build/libs/*.jar /app/Citizen-App-0.0.1-SNAPSHOT.jar

# Expose the port that your Spring Boot app runs on
EXPOSE 8080

# Set the entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "Citizen-App-0.0.1-SNAPSHOT.jar"]
