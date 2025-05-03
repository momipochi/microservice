# First stage: Build the JAR using Maven
FROM maven:3.9.9-sapmachine-21 AS builder
WORKDIR /app

COPY pom.xml .
COPY src ./src
COPY /settings.xml /root/.m2/settings.xml

# Add this before RUN
ARG MAVEN_BUILD_ARGS=""

# Modify the build step
RUN mvn clean package -DskipTests $MAVEN_BUILD_ARGS

# Second stage: Run the JAR
FROM openjdk:21-jdk-slim
WORKDIR /app

COPY --from=builder /app/target/proudcts-query-0.0.1-SNAPSHOT.jar java.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "java.jar"]
