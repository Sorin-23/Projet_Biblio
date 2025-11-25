FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY ./target/*.war biblio.war
CMD [ "java", "-jar", "biblio.war" ]
EXPOSE 8080