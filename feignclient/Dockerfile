FROM openjdk:11.0.9.1-jre-buster
ADD build/libs/feignclient-0.0.1-SNAPSHOT.jar /
ENTRYPOINT ["java", "-jar", "/feignclient-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=docker"]
EXPOSE 8080