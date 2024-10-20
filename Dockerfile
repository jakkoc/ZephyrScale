FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY src ./src
COPY pom.xml .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY --from=build /app/target/*.jar time-service.jar
ENV PORT=8080
ENTRYPOINT ["java", "-jar", "time-service.jar"]