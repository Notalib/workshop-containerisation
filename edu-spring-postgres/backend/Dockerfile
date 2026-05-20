# syntax=docker/dockerfile:1.6

# Stage 1: Build
FROM maven:3.9.14-eclipse-temurin-25-alpine AS builder

RUN apk upgrade --available --no-cache

WORKDIR /app

COPY pom.xml .

RUN mvn --batch-mode --errors --quiet dependency:go-offline

# Copy the relevant directory
COPY src ./src

# If check passes, build the jar
RUN mvn --batch-mode --errors --quiet clean package -DskipTests

# Extract the jar file using an efficient layout
RUN java -Djarmode=tools -jar target/project.jar extract --layers --destination extracted

# Stage 2: Run the application
FROM eclipse-temurin:25.0.2_10-jre-alpine-3.23

LABEL org.opencontainers.image.source=https://github.com/notalib/workshop-containerisation
LABEL org.opencontainers.image.description="Spring Boot example application"

# Upgrade packages
RUN apk upgrade --available --no-cache

# Copy the extracted jar contents from the builder container into the working directory in the runtime container
# Every copy step creates a new docker layer
# This allows docker to only pull the changes it really needs
COPY --from=builder /app/extracted/dependencies/ ./
COPY --from=builder /app/extracted/spring-boot-loader/ ./
COPY --from=builder /app/extracted/snapshot-dependencies/ ./
COPY --from=builder /app/extracted/application/ ./

COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

# Add a non-root user
RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring:spring

ENTRYPOINT ["/entrypoint.sh"]
