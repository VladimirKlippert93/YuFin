FROM openjdk:19

ENV ENVIROMENT=prod

MAINTAINER Vladimir Klippert <vladimir.klippert93@gmail.com>

EXPOSE 8080

ADD backend/target/app.jar app.jar

CMD ["sh", "-c", "java -jar /app.jar"]