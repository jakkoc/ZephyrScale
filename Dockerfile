FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} time-service.jar
ENTRYPOINT ["java", "-jar", "time-service.jar"]