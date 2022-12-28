FROM openjdk:8
ADD  target/gateway-service-v1.0.0.jar gateway-service-v1.0.0.jar
expose 8080
ENTRYPOINT [ "java","-jar","gateway-service-v1.0.0.jar" ]