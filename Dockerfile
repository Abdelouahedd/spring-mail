FROM maven:3.8-jdk-11 as build
WORKDIR /app
COPY pom.xml pom.xml
COPY src/ src/
RUN mvn clean test
RUN mvn package


FROM openjdk:11
WORKDIR /app
COPY --from=build /app/target/spring-mail-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

