FROM maven:3.8.5-amazoncorretto-17 AS build
RUN mkdir /project
COPY . /project
WORKDIR /project
EXPOSE 10001
ENTRYPOINT [ "mvn", "clean", "spring-boot:run"]