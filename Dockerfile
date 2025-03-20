FROM openjdk:17-oracle
COPY zama-registrar/build/libs/*.jar zama-registrar.jar
EXPOSE 9001
ENTRYPOINT [ "java", "-jar", "zama-registrar.jar" ]