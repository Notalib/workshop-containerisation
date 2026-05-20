FROM ubuntu:26.04

# TODO: RUN a command to install 'openjdk-21-jdk' package

# TODO: COPY Hello.java from host into container

# TODO: RUN a command to build Hello.java file

# Here we copy in the static message.txt file,
# to the path our Hello.java class expects it to be at inside the container.
COPY message.txt /data/message.txt

# TODO: Fix the ENTRYPOINT to start the built Hello.class
ENTRYPOINT ["java", "insert-my-java-class"]

# When above TODOs has been completed try to build & run:
#   BUILD: docker build . --progress=plain --tag java-app
#   RUN:   docker run java-app
