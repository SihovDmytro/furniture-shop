# ---- build stage ----
FROM maven:3.8-eclipse-temurin-11 AS build
WORKDIR /build
COPY pom.xml .
# Download dependencies separately so they are cached by Docker
RUN mvn dependency:go-offline -q
COPY src ./src
RUN mvn package -DskipTests -q

# ---- runtime stage ----
FROM eclipse-temurin:11-jre
WORKDIR /app
COPY --from=build /build/target/furniture-shop-0.0.1-SNAPSHOT.jar app.jar
# Directory for user-uploaded avatars (mounted as a named volume in compose)
RUN mkdir -p /app/avatars
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

