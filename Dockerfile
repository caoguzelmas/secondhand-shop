#build stage
FROM maven:3.6.3-jdk-11 AS build
COPY pom.xml .
COPY secondhand-shop-app /secondhand-shop-app
COPY secondhand-shop-user /secondhand-shop-user
RUN mvn clean install

FROM openjdk:11
COPY --from=build /secondhand-shop-app/target/*.jar secondhand-shopping.jar
ENTRYPOINT ["java", "-jar", "secondhand-shopping.jar"]