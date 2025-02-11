FROM openjdk:21-jdk-slim AS build

WORKDIR /app

COPY . .

RUN ./gradlew clean build -x test --no-daemon

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "-Xmx512m"]