# Use an Ubuntu base image with a specific version
FROM mxnet/python:1.9.1_native_py3
FROM openjdk:8-jre-alpine

COPY /build/libs/app.jar app.jar

CMD ["java", "-jar", "/app.jar"]