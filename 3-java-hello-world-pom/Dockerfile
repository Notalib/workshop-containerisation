# TODO: Pick an appropriate image with preinstalled JDK and Maven tools.
FROM <insert-base-image> AS build (find an image on https://hub.docker.com)

# TODO: COPY the ./src folder into the container

# TODO: RUN a command to build the project with maven

# TODO: Pick an appropriate image with preinstalled JRE.
FROM <insert-runtime-image> AS runtime (find an image on https://hub.docker.com)

COPY message.txt /data/message.txt

# TODO: COPY the built jar into the runtime stage (--from=build)

# TODO: Fix the ENTRYPOINT below to start the built jar.
ENTRYPOINT ["java", "-jar", "my-built-app.jar"]
