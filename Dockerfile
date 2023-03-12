FROM maven:3-openjdk-17-slim

COPY / source

RUN mvn -f source/pom.xml clean package

COPY /target/Application-0.0.1-SNAPSHOT.jar /usr/app/app.jar
RUN rm -rf source target

EXPOSE 8008

CMD ["java", "-jar", "/usr/app/app.jar"]