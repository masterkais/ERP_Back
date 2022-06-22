FROM  openjdk:11
VOLUME /tmp
ARG JAR_FILE
COPY target/msGestionMagasin-1.jar msGestionMagasin-1.jar
ENTRYPOINT ["java","-jar","/msGestionMagasin-1.jar"]