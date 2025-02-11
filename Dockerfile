FROM maven:3.8.5-jdk-11-slim AS build
VOLUME /tmp
COPY .git /home/app/.git
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -e -f /home/app/pom.xml clean package -DskipTests

RUN chown -R 1001:1001 /home/app

FROM adoptopenjdk/openjdk11:alpine-jre
VOLUME /tmp
COPY --from=build /home/app/target/cio-creditmgmt-asmt-migration-0.0.1-SNAPSHOT.jar app.jar

RUN mkdir /home/app/
RUN chown -R 1001:1001 /home/app/


RUN mkdir /home/app/logs/
RUN chown -R 1001:1001 /home/app/logs/

ENV SPRING_PROFILES_ACTIVE=non-prod
ENTRYPOINT ["java", "-Djava.security.edg=file:/dev/./urandom","-jar","app.jar"]