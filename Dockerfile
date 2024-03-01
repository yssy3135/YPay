FROM openjdk:11-slim-stretch
EXPOSE 8000
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["JAVA", "-jar", "/app.jar"]