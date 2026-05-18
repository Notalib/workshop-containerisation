# TODO: FROM your-java-maven-sdk AS build (find an image on https://hub.docker.com)

# TODO: copy in the source

# TODO: build the project

FROM your-java-runtime (find an image on https://hub.docker.com)

# TODO: copy the built jar into the runtime stage (--from=build)

COPY message.txt /data/message.txt

# TODO: Change the entrypoint below to start the built jar.
ENTRYPOINT ["java", "-jar", "my-built-java-jar"]
