FROM openjdk:11-jdk-slim
WORKDIR /app
COPY target/atm-eureka-1.0-SNAPSHOT.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","app.jar"] 