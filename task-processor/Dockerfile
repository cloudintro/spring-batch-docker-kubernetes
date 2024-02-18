FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8080
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar", "-Dserver.port=8080", "-Dspring.profiles.active=prod", "/app.jar"]
