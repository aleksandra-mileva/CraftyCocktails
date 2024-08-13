FROM amazoncorretto:17.0.6
COPY target/crafty-cocktails-app-2.7.5.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]