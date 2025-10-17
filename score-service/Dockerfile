FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/score-service.jar score-service.jar

CMD ["java", "-jar", "score-service.jar"]