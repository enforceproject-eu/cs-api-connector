FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /tmp
RUN git clone https://github.com/enforceproject-eu/cs-module-parent
WORKDIR /tmp/cs-module-parent
RUN mvn clean install -DskipTests

WORKDIR /tmp
RUN git clone https://github.com/enforceproject-eu/cs3-db-model
WORKDIR /tmp/cs3-db-model
RUN mvn clean install -DskipTests

WORKDIR /tmp
RUN git clone https://github.com/enforceproject-eu/cs1-module
WORKDIR /tmp/cs1-module
RUN mvn clean install -DskipTests

WORKDIR /tmp
RUN git clone https://github.com/enforceproject-eu/cs5-module
WORKDIR /tmp/cs5-module
RUN mvn clean install -DskipTests

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Makes use of environment variables for configuration
ENTRYPOINT ["java", "-jar", "app.jar"]