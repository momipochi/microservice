# First stage: Build the JAR
FROM gradle:jdk21 AS builder
WORKDIR /app
COPY . .
RUN ./gradlew bootJar

# Second stage: Run the JAR
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy the wait-for-it.sh script from the scripts folder
COPY scripts/wait-for-it.sh /app/scripts/wait-for-it.sh

# Make sure the wait-for-it.sh script is executable
RUN chmod +x /app/scripts/wait-for-it.sh

# Copy the JAR file
COPY --from=builder /app/build/libs/productservice-0.0.1-SNAPSHOT.jar java.jar

# Expose the port (make sure it's the right one for your service)
EXPOSE 8080

# Wait for Postgres to be ready, then run the Java application
ENTRYPOINT ["/bin/sh", "-c", "./scripts/wait-for-it.sh postgres:5432 -- java -jar java.jar"]
