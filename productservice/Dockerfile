# First stage: Build the JAR using Maven
FROM maven:3.9.9-sapmachine-21 AS builder
WORKDIR /app

# Copy the pom.xml and the source code
COPY pom.xml .
COPY src ./src

# Build the JAR using Maven
RUN mvn clean package -DskipTests

# Second stage: Run the JAR
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy the wait-for-it.sh script from the scripts folder
COPY scripts/wait-for-it.sh /app/scripts/wait-for-it.sh

# Make sure the wait-for-it.sh script is executable
RUN chmod +x /app/scripts/wait-for-it.sh

# Copy the JAR file built by Maven
COPY --from=builder /app/target/productservice-0.0.1-SNAPSHOT.jar java.jar

# Expose the port (make sure it's the right one for your service)
EXPOSE 8080

# Wait for Postgres to be ready, then run the Java application
ENTRYPOINT ["/bin/sh", "-c", "./scripts/wait-for-it.sh postgres:5432 -- java -jar java.jar"]
