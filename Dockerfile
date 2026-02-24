# Use uma imagem base do JDK 17
FROM eclipse-temurin:17-jdk-alpine

COPY build/libs/usuario-0.0.1-SNAPSHOT.jar /app/usuario.jar

# porta da aplicação
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "/app/usuario.jar"]
