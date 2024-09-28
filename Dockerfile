FROM openjdk:8-jdk
LABEL authors="artibix"

RUN apt-get update && apt-get install -y maven

WORKDIR /usr/src/app

CMD ["mvn", "spring-boot:run", "-pl", "ruoyi-admin"]