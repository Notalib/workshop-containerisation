# TODO: FROM your-java-maven-sdk-image AS build (find an image on https://hub.docker.com)

# TODO: copy in the source

# TODO: build the project

FROM your-java-runtime (find an image on https://hub.docker.com)

# TODO: copy the built java class into the runtime stage (--from=build)

COPY message.txt /data/message.txt

# TODO: Set the entrypoint to start the Hello.class

ENTRYPOINT ["java", "my-built-java-class"]
