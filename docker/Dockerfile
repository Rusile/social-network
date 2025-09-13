FROM gradle:8.8-jdk17 AS build
WORKDIR /app

COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle

RUN gradle --no-daemon dependencies

COPY . .
RUN gradle --no-daemon clean bootJar

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]