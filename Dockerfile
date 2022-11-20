FROM adoptopenjdk/openjdk11

MAINTAINER Yohan Bouali

VOLUME ["/eshop-ms-basket"]

WORKDIR /eshop-ms-basket

ARG JAR_FILE=target/eshop-ms-basket-1.0.0-SNAPSHOT.jar

ARG STARTUP_SCRIPT=scripts/startup.sh

COPY ${JAR_FILE} app.jar

COPY ${STARTUP_SCRIPT} startup.sh

RUN chmod 755 startup.sh

CMD ["sh", "startup.sh"]

EXPOSE 8080