##### BUILD STAGE #####
FROM maven:3.9.10-eclipse-temurin-24 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

## Runs tests and builds the app
RUN mvn clean package -DskipTests

##### RUN STAGE #####
FROM eclipse-temurin:24-jdk-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar user-app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "user-app.jar"]