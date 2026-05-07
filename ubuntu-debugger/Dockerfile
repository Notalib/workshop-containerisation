FROM ubuntu:24.04

LABEL org.opencontainers.image.source=https://github.com/notalib/workshop-containerisation
LABEL org.opencontainers.image.description="Ubuntu-based debugger image"

RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y jq yq \
      dnsutils findutils net-tools openssl openssh-client \
      nmap curl \
    && rm -rf /var/lib/apt/lists/* \
    && echo 'export PS1="[ubuntu-debugger]\$ "' >> /root/.bash_profile

CMD ["/bin/bash", "--login"]
