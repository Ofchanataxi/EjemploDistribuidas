FROM eclipse-temurin:17-jdk
WORKDIR /app

# Exponer el puerto donde Spring Boot escucha (configurable por SERVER_PORT)
EXPOSE 8080

COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
