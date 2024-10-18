# Usa una imagen de Maven genérica con Java 21
FROM maven:3.9.4-eclipse-temurin AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Usa una imagen ligera de Java 21 para ejecutar la aplicación
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
