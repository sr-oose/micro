FROM openjdk:11.0.9.1-jre-buster
ADD build/libs/gateway-0.0.1-SNAPSHOT.jar /
ENTRYPOINT ["java", "-jar", "/gateway-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=docker"]
EXPOSE 8080