# build stage
FROM maven:3-jdk-12 AS build

COPY pom.xml /code/
COPY src/ /code/src

WORKDIR /code


RUN mvn clean install


# run app
FROM openjdk:12

## copy jar from codingchallenge to an artifacts dir
COPY --from=build /code/target/sampleparserkotlinproject-1.0-SNAPSHOT-jar-with-dependencies.jar /sampleparserkotlinproject.jar



ENTRYPOINT ["java", "-jar", "sampleparserkotlinproject.jar"]
