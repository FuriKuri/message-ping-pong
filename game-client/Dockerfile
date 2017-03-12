FROM openjdk:8-alpine
MAINTAINER tf.pack@gmail.com

RUN adduser -S game-app
USER game-app

WORKDIR /app
COPY ./build/libs/*.jar /app/game-app.jar

EXPOSE 8080

CMD ["java", "-jar", "-Djava.security.egd=file:/dev/./urandom", "./game-app.jar", "-XX:+ExitOnOutOfMemoryError"]
