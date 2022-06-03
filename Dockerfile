FROM adoptopenjdk:11-jre-hotspot
MAINTAINER cristhianvg.com
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]