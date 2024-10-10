FROM openjdk:17

ENV BASE_URL = ${BASE_URL}
ENV SECRET_KEY = ${SECRET_KEY}
ENV REDIS_HOST = ${REDIS_HOST}
ENV REDIS_PORT = ${REDIS_PORT}
ENV SERVER_HOST = ${SERVER_HOST}
ENV SERVER_PORT = ${SERVER_PORT}

RUN mkdir - p app
WORKDIR /app
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar","-Duser.timezone=Asia/Seoul","-Dspring.profiles.active=prod","app.jar"]