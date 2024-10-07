FROM openjdk:17

ENV BASE_URL = ${BASE_URL}
ENV SECRET_KEY = ${SECRET_KEY}
ENV REDIS_HOST = ${REDIS_HOST}

RUN mkdir - p app
WORKDIR /app
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar","-Duser.timezone=Asia/Seoul","-Dspring.profiles.active=prod","app.jar"]