FROM openjdk:8-jre-alpine

ARG JAR_FILE
ADD ${JAR_FILE} app.jar

ENTRYPOINT ["/usr/bin/java", "-Dspring.profiles.active=registered-config-server", "-Djava.security.egd=file:/dev/./urandom", "-Xmx200m", "-jar", "app.jar"]