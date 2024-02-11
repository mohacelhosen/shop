#Set the base image
FROM openjdk:17

#Set working directory
WORKDIR /app

#Copy the target folder from host machine to docker
ADD ./target/team-codex.jar  /app/team-codex.jar

#Run the spring boot jar file in docker and expose the port(8000) number of the application
ENTRYPOINT ["java", "-jar", "/app/team-codex.jar"]