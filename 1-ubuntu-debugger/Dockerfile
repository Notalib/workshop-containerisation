FROM ubuntu:26.04

LABEL org.opencontainers.image.source=https://github.com/notalib/workshop-containerisation
LABEL org.opencontainers.image.description="Ubuntu-based debugger image"

RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y jq yq \
      dnsutils findutils net-tools openssl openssh-client \
      nmap curl \
    && rm -rf /var/lib/apt/lists/* \
    && echo 'export PS1="[ubuntu-debugger]\$ "' >> /root/.bash_profile

# Print Linux distribution info
RUN cat /etc/os-release

# Check if the commands are available now
RUN command -v curl || command -v nslookup || echo "Network utils NOT found!"

# We don't need to be root anymore,
# switch to Ubuntu's builtin unpriviledged user.
USER ubuntu

# Print interesting information when the container starts.
RUN echo "echo Welcome user $(whoami)! The container is running!" >> ~/.bash_profile \
    && echo "echo Distro:; cat /etc/os-release | head -n 1; echo" >> ~/.bash_profile \
    && echo "echo Kernel:; uname -a; echo" >> ~/.bash_profile

ENTRYPOINT ["/bin/bash", "--login"]

# TODO: When the TODO has been completed try to build & run the container (see README for commands).
