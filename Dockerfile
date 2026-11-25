FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /tmp
RUN git clone https://github.com/enforceproject-eu/cs-module-parent
WORKDIR /tmp/cs-module-parent
RUN git checkout v1.0.0
RUN mvn clean install -DskipTests

WORKDIR /tmp
RUN git clone https://github.com/enforceproject-eu/common-data-model
WORKDIR /tmp/common-data-model
RUN git checkout v1.0.0
RUN mvn clean install -DskipTests 

WORKDIR /tmp
RUN git clone https://github.com/enforceproject-eu/geoquest-module
WORKDIR /tmp/geoquest-module
RUN git checkout v1.0.0
RUN mvn clean install -Pgenerate-remote-api -DskipTests 

WORKDIR /tmp
RUN git clone https://github.com/enforceproject-eu/cs3-db-model
WORKDIR /tmp/cs3-db-model
RUN mvn clean install -DskipTests

WORKDIR /tmp
RUN git clone https://github.com/enforceproject-eu/cs4-db-model
WORKDIR /tmp/cs4-db-model
RUN git checkout v1.0.0
RUN mvn clean install -DskipTests

WORKDIR /tmp
RUN git clone https://github.com/enforceproject-eu/cs5-module
WORKDIR /tmp/cs5-module
RUN git checkout v1.0.0
RUN mvn clean install -DskipTests

WORKDIR /tmp
RUN git clone https://github.com/enforceproject-eu/cs8-module
WORKDIR /tmp/cs8-module
RUN git checkout v1.0.0
RUN mvn clean install -DskipTests

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

RUN mkdir /usr/local/share/enforce-data

# Makes use of environment variables for configuration
ENTRYPOINT ["java", "-jar", "app.jar"]