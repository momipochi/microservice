# First stage: Build the JAR
FROM gradle:jdk24 AS builder
WORKDIR /app
COPY . .
RUN ./gradlew bootJar

# Second stage: Run the JAR
FROM openjdk:24-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar java.jar
EXPOSE 8040
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "java.jar"]