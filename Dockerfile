FROM maven:3.9.9-eclipse-temurin-21 AS buildstage

WORKDIR /usr/app

COPY . .

RUN mvn -DskipTests clean package


FROM eclipse-temurin:21-jre-jammy AS runstage

WORKDIR /usr/app

COPY --from=buildstage /usr/app/target/mydebt-0.0.1-SNAPSHOT.jar .

RUN apt-get update \
    && apt-get install -y --no-install-recommends fontconfig fonts-dejavu-core \
    && rm -rf /var/lib/apt/lists/* \
    && mkdir -p logs/

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/usr/app/mydebt-0.0.1-SNAPSHOT.jar"]
