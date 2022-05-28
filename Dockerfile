FROM adoptopenjdk:11-jre-hotspot
MAINTAINER cristhianvg.com
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} demo-1.0.jar
ENTRYPOINT ["java","-jar","demo-1.0.jar"]