FROM adoptopenjdk:11-jre-openj9

RUN groupadd -g 1000 checkout \
        && useradd -u 1000 -g checkout checkout

WORKDIR /app

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app/app.jar

USER checkout:checkout

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
