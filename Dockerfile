FROM openjdk:8-jre-alpine

COPY target/mydebt-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
RUN apk --update add fontconfig ttf-dejavu
RUN mkdir -p logs/
WORKDIR /usr/app
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/usr/app/mydebt-0.0.1-SNAPSHOT.jar"]