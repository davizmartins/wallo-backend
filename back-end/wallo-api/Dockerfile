# Etapa 1: Build - compila o projeto e gera o .jar
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app
COPY back-end/wallo-api/pom.xml .
COPY back-end/wallo-api/src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Run - imagem leve só para executar
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/target/wallo-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]