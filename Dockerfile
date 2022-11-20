FROM adoptopenjdk/openjdk11

MAINTAINER Yohan Bouali

VOLUME ["/eshop-ms-basket"]

WORKDIR /eshop-ms-basket

ARG JAR_FILE=target/eshop-ms-basket-1.0.0-SNAPSHOT.jar

ARG STARTUP_SCRIPT=scripts/startup.sh

COPY ${JAR_FILE} app.jar

# On copie notre script qui lancera le jar.
COPY ${STARTUP_SCRIPT} startup.sh

# On donne les permissions nécessaires au fichier pour s'executer. 
RUN chmod 755 startup.sh

# On execute le script qui lance le jar au lancement du container.
CMD ["sh", "startup.sh"]

EXPOSE 8080
