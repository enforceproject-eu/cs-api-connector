FROM amd64/eclipse-temurin:17-jdk-ubi9-minimal

LABEL MAINTAINER="b.pross@52north.org"

USER root

VOLUME /tmp
COPY ../../../target/*.jar /usr/local/lib/app.jar

#Note: local add Java Opt: -Dspring.profiles.active=[dev|test|prod]
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS}  -Djava.security.egd=file:/dev/./urandom -jar /usr/local/lib/app.jar"]