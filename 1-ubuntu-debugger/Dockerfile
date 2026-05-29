FROM ubuntu:26.04

# You are now inside a clean Ubuntu container 🧐

# HINT: You can RUN useful commands inside the Ubuntu container while building.
RUN echo "Hello container-builder, you are currently $(whoami)" && sleep 3

# Print Linux distribution info
RUN cat /etc/os-release

# TODO: We want to add curl and nslookup to this container
#       Add RUN command(s) to apt-get install 'curl' and 'dnsutils' packages.

# Check if the commands are available now
RUN command curl || command nslookup || echo "Network utils NOT found!"

# We don't need to be root anymore,
# switch to Ubuntu's builtin unpriviledged user.
USER ubuntu

# Print interesting information when the container starts.
RUN echo "echo Welcome user $(whoami)! The container is running!" >> ~/.bash_profile \
    && echo "echo Distro:; cat /etc/os-release | head -n 1; echo" >> ~/.bash_profile \
    && echo "echo Kernel:; uname -a; echo" >> ~/.bash_profile

ENTRYPOINT ["/bin/bash", "--login"]

# TODO: When the TODO has been completed try to build & run the container (see README for commands).
