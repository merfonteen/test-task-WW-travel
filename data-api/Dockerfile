FROM eclipse-temurin:21-jre-alpine as runtime

WORKDIR /app

ARG JAR_FILE=target/data-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]

EXPOSE 8080
