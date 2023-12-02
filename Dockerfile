FROM openjdk:17

ARG JAR_FILE=build/libs/gremio-0.0.1.jar

RUN mkdir /app
RUN mkdir /app/config
RUN mkdir /app/logs

COPY ${JAR_FILE} /app/app.jar


ENTRYPOINT ["sh", "-c", "java -Duser.timezone=UTC -Dspring.profiles.active=prod -jar -Dspring.config.location=classpath:/application.properties,/app/config/ /app/app.jar"]