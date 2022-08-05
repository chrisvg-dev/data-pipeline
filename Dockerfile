FROM maven:3-jdk-11 AS build
MAINTAINER cristhianvg.com

COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM gcr.io/distroless/java
COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","/usr/app/app.jar"]