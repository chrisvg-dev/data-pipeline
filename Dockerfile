FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /home/apps
ARG JAR_FILE=target/v1-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]