#java 21
FROM eclipse-temurin:17-jdk

WORKDIR /app

#copia el jar construido
COPY ./target/test-0.0.1-SNAPSHOT.jar ./test-0.0.1-SNAPSHOT.jar

#puerto donde escucha tu app (coincide con application.properties SERVER_PORT por defecto)
EXPOSE 8081

ENTRYPOINT ["java","-jar","test-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=docker"]
