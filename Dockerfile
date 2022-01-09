#
# Package stage
#
FROM openjdk:8-jre-slim
COPY ./target/app-1.0.0-SNAPSHOT.jar /usr/local/lib/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]
